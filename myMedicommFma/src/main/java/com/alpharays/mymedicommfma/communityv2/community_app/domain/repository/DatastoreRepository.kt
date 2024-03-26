package com.alpharays.mymedicommfma.communityv2.community_app.domain.repository

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.CommunityPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface DatastoreRepository {
    val getCurrentPostStateFlow: MutableStateFlow<CommunityPost?>

    suspend fun setCommunityPost(post: CommunityPost)

    suspend fun removeCommunityPost(id: String)

    fun getCommunityPost(): Flow<CommunityPost?>

    suspend fun removeAllAlerts()
}