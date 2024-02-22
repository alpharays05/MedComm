package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.allposts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityPost(
    @SerializedName("__v") val v: String? = null,
    @SerializedName("_id") val id: String? = null,
    val aboutDoc: String? = null,
    val avatar: String? = null,
    val comments: List<String>? = null,
    val postContent: String? = null,
    val postTitle: String? = null,
    val posterId: String? = null,
    val posterName: String? = null,
    val reactions: Reactions? = null,
)

@Serializable
data class Reactions(
    val celebrate: List<ReactionDetails>? = null,
    val funny: List<ReactionDetails>? = null,
    val insightful: List<ReactionDetails>? = null,
    val like: List<ReactionDetails>? = null,
    val love: List<ReactionDetails>? = null,
    val support: List<ReactionDetails>? = null,
)

@Serializable
data class ReactionDetails(
    val userId: String? = null,
    val userName: String? = null,
)