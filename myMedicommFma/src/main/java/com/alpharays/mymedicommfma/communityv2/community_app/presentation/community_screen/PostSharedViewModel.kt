package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import androidx.lifecycle.ViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PostSharedViewModel @Inject constructor() : ViewModel() {
    private val _postContentState = MutableStateFlow<CommunityPost?>(CommunityPost())
    val postContentState: StateFlow<CommunityPost?> = _postContentState

    fun setOrUpdate(post: CommunityPost) {
        _postContentState.value = post
        println("postContent updated :: $post")
    }

    fun clearPostContentState() {
        println("postContent clear request")
        _postContentState.value = null
    }

    override fun onCleared() {
        super.onCleared()
        println("postContent onCleared() viewModel")
        _postContentState.value = null
    }
}