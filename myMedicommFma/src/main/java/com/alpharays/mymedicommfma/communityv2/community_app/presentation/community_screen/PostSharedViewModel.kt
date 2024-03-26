package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.DatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostSharedViewModel @Inject constructor(
    private val useCase: DatastoreUseCase,
) : ViewModel() {
    private val _postContentState = MutableStateFlow<CommunityPost?>(CommunityPost())
    val postContentState: StateFlow<CommunityPost?> = _postContentState

    init {
        getCurrentPostState()
    }

    fun setCurrentPostState(post: CommunityPost) {
        viewModelScope.launch {
            useCase.setCommunityPost(post)
        }
    }

    private fun getCurrentPostState() {
        useCase
            .getCommunityPost()
            .onEach { result ->
                _postContentState.value = result
            }
            .launchIn(viewModelScope)
    }

    fun removeAllPosts() {
        viewModelScope.launch {
            useCase.removeAllPosts()
        }
    }
}