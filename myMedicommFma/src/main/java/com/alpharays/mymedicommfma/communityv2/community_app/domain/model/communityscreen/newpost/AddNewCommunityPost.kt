package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost

import kotlinx.serialization.Serializable

@Serializable
data class AddNewCommunityPost(
    val postTitle: String? = null,
    val postContent: String? = null
)