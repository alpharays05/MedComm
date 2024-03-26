package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.UNEXPECTED_ERROR
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.AllCommunityPostsParent
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    context: Context,
    private val networkObserver: NetworkConnectivityObserver,
    private val communityUseCase: CommunityUseCase,
) : ViewModel() {
    private val _allCommunityPostsStateFlow = MutableStateFlow(CommunityAllPostsState())
    val allCommunityPostsStateFlow: StateFlow<CommunityAllPostsState> = _allCommunityPostsStateFlow.asStateFlow()

    private val _addNewCommunityPostStateFlow = MutableStateFlow(NewPostResponseState())
    val addNewCommunityPostStateFlow: StateFlow<NewPostResponseState> = _addNewCommunityPostStateFlow.asStateFlow()

    private var token = ""

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()

    init {
        // token may not change during a session (login-logout) - so using init{} else could have used : get()
        token = CommunityUtils.getAuthToken(context)
        observeNetworkConnectivity()
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkObserver.observe().collect { status ->
                if(_networkStatus.value != ConnectivityObserver.Status.Available && status == ConnectivityObserver.Status.Available){
                    getAllCommunityPosts()
                }
                _networkStatus.value = status
            }
        }
    }

    fun refreshCommunityPosts() {
        if (_networkStatus.value == ConnectivityObserver.Status.Available) {
            getAllCommunityPosts()
        }
    }

    private fun getAllCommunityPosts() {
        println("calling_all_community_posts")
        communityUseCase(token).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _refreshing.value = true
                    val state = CommunityAllPostsState(isLoading = true)
                    _allCommunityPostsStateFlow.value = state
                }

                is ResponseResult.Success -> {
                    _refreshing.value = false
                    val state = CommunityAllPostsState(allPosts = result.data)
                    _allCommunityPostsStateFlow.value = state
                }

                is ResponseResult.Error -> {
                    _refreshing.value = false
                    val state = CommunityAllPostsState(error = result.message ?: UNEXPECTED_ERROR)
                    _allCommunityPostsStateFlow.value = state
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addNewCommunityPost(addNewCommunityPost: AddNewCommunityPost) {
        communityUseCase(token, addNewCommunityPost).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _addNewCommunityPostStateFlow.value = NewPostResponseState(isLoading = true)
                }

                is ResponseResult.Success -> {
                    _addNewCommunityPostStateFlow.value =
                        NewPostResponseState(newPostResponse = result.data)
                }

                is ResponseResult.Error -> {
                    _addNewCommunityPostStateFlow.value =
                        NewPostResponseState(error = result.message ?: UNEXPECTED_ERROR)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class CommunityAllPostsState(
    var isLoading: Boolean? = false,
    var allPosts: AllCommunityPostsParent? = null,
    var error: String? = null,
)

data class NewPostResponseState(
    var isLoading: Boolean? = false,
    var newPostResponse: NewPostResponse? = null,
    var error: String? = null,
)