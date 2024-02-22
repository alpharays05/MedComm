package com.alpharays.mymedicommfma.community_app.presentation.community_screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants.UNEXPECTED_ERROR
import com.alpharays.mymedicommfma.common.nuttiessdk.ResponseResult
import com.alpharays.mymedicommfma.community_app.MedicoUtils
import com.alpharays.mymedicommfma.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.allposts.AllCommunityPostsParent
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.community_app.domain.usecase.CommunityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CommunityViewModel(
    private val communityUseCase: CommunityUseCase
) : ViewModel() {
    private val _allCommunityPostsStateFlow = MutableStateFlow(CommunityAllPostsState())
    val allCommunityPostsStateFlow: StateFlow<CommunityAllPostsState> = _allCommunityPostsStateFlow.asStateFlow()

    private val _addNewCommunityPostStateFlow = MutableStateFlow(NewPostResponseState())
    val addNewCommunityPostStateFlow: StateFlow<NewPostResponseState> = _addNewCommunityPostStateFlow.asStateFlow()

    private val _allCachedCommunityPosts = MutableStateFlow(CommunityAllPostsState())
    val allCachedCommunityPosts: StateFlow<CommunityAllPostsState> = _allCachedCommunityPosts.asStateFlow()

    private var token = ""
    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Lost)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus

    init {
        token = CommunityUtils.getAuthToken()
    }

    fun updateNetworkStatus(status: ConnectivityObserver.Status) {
        _networkStatus.value = status
        if(status == ConnectivityObserver.Status.Available){
            MedicoUtils.Companion.NetworkCheck.setNetworkStatus(false)
            getAllCommunityPosts()
        }
        if(status == ConnectivityObserver.Status.Lost){
            MedicoUtils.Companion.NetworkCheck.setNetworkStatus(true)
        }
    }

    fun retryGettingPosts() = getAllCommunityPosts()

    private fun getAllCommunityPosts() {
        communityUseCase.getAllCommunityPosts(token).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = CommunityAllPostsState(isLoading = true)
                    _allCommunityPostsStateFlow.value = state
                }

                is ResponseResult.Success -> {
                    val state = CommunityAllPostsState(allPosts = result.data)
                    _allCommunityPostsStateFlow.value = state
                }

                is ResponseResult.Error -> {
                    val state = CommunityAllPostsState(error = result.message ?: UNEXPECTED_ERROR)
                    _allCommunityPostsStateFlow.value = state
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addNewCommunityPost(addNewCommunityPost: AddNewCommunityPost) {
        communityUseCase.addNewCommunityPost(token, addNewCommunityPost).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _addNewCommunityPostStateFlow.value = NewPostResponseState(isLoading = true)
                }

                is ResponseResult.Success -> {
                    _addNewCommunityPostStateFlow.value = NewPostResponseState(newPostResponse = result.data)
                }

                is ResponseResult.Error -> {
                    _addNewCommunityPostStateFlow.value = NewPostResponseState(error = result.message ?: UNEXPECTED_ERROR)
                }
            }
        }.launchIn(viewModelScope)
    }


    //  ************   room db - cached data   ************
    private fun setAllCachedPosts(){

    }

    private fun getAllCachedPosts(){

    }
}

data class CommunityAllPostsState(
    var isLoading: Boolean? = false,
    var allPosts: AllCommunityPostsParent? = null,
    var error: String? = null
)

data class NewPostResponseState(
    var isLoading: Boolean? = false,
    var newPostResponse: NewPostResponse? = null,
    var error: String? = null
)