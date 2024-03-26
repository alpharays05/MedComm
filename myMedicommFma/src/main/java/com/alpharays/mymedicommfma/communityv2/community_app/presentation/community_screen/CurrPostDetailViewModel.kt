package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CurrPostDetailViewModel @Inject constructor(
    context: Context,
    private val communityUseCase: CommunityUseCase,
) : ViewModel() {
    private var _allCommentsStateFlow = MutableStateFlow(AllCommentsState())
    val allCommentsStateFlow: StateFlow<AllCommentsState> = _allCommentsStateFlow.asStateFlow()

    init {
        val token = CommunityUtils.getAuthToken(context)
        val postId = CommunityUtils.getOneTimePostId(context)
        if (token.isNotEmpty() && postId.isNotEmpty()) {
            println("currPostDetailViewModel : token : $token :: postId : $postId")
            getAllComments(token, AllCommentsRequestBody(postId))
        }
    }

    private fun getAllComments(token: String, postId: AllCommentsRequestBody) {
        communityUseCase(token, postId).onEach { response ->
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
}

data class AllCommentsState(
    var isLoading: Boolean? = false,
    var allComments: List<AllCommentsData?>? = null,
    var error: String? = null,
)