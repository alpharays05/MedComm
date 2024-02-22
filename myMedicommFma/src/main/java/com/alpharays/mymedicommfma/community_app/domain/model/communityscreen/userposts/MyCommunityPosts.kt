package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.userposts

import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.allposts.Reactions
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// img url
// time of posting
@Serializable
data class MyCommunityPosts(
    @SerializedName("_id") val id: String? = null,
    val posterId: String? = null,
    val posterName: String? = null,
    val postTitle: String? = null,
    val postContent: String? = null,
    val reactions: Reactions? = null,
    val comments: List<String>? = null,
    @SerializedName("__v") val v: Int,
)