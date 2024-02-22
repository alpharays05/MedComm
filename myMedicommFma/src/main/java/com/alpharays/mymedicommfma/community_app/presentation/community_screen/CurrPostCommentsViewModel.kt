package com.alpharays.mymedicommfma.community_app.presentation.community_screen

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.common.nuttiessdk.ResponseResult
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsData
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsRequestBody
import com.alpharays.mymedicommfma.community_app.domain.usecase.CommunityUseCase
import com.alpharays.mymedicommfma.community_app.community_utils.CommunityUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CurrPostCommentsViewModel(
    private val communityUseCase: CommunityUseCase,
) : ViewModel(), DefaultLifecycleObserver {
    private var _allCommentsStateFlow = MutableStateFlow(AllCommentsState())
    val allCommentsStateFlow: StateFlow<AllCommentsState> = _allCommentsStateFlow.asStateFlow()

    init {
        CommunityUtils
        val token = CommunityUtils.getAuthToken()
        val postId = CommunityUtils.getOneTimePostId()
        if (token.isNotEmpty() && postId.isNotEmpty()) {
            println("token : $token :: postId : $postId")
            getAllComments(token, AllCommentsRequestBody(postId))
        }
    }

    private fun getAllComments(token: String, postId: AllCommentsRequestBody) {
        communityUseCase.getAllCurPostComments(token, postId).onEach { response ->
            when (response) {
                is ResponseResult.Loading -> {
                    _allCommentsStateFlow.value = AllCommentsState(isLoading = true)
                }

                is ResponseResult.Success -> {
                    val result = response.data?.allComments
                    _allCommentsStateFlow.value = AllCommentsState(allComments = result)
                }

                is ResponseResult.Error -> {
                    val error = response.data?.error
                    _allCommentsStateFlow.value = AllCommentsState(error = error)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        CommunityUtils.setOneTimePostId("")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        CommunityUtils.setOneTimePostId("")
    }
}


data class AllCommentsState(
    var isLoading: Boolean? = false,
    var allComments: List<AllCommentsData?>? = null,
    var error: String? = null,
)