package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommunityPostsParent(
    @SerializedName("data") val communityPostData: List<CommunityPost>? = null,
    val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    val success: String? = null,
)