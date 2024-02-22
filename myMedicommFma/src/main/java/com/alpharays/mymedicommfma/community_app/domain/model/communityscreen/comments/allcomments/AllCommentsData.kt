package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommentsData(
    @SerializedName("commentContent") val commentContent: String? = null,
    @SerializedName("commentTime") val commentTime: String? = null,
    @SerializedName("commentedByUserId") val commentedByUserId: String? = null,
    @SerializedName("commentedByUserName") val commentedByUserName: String? = null,
    @SerializedName("_id") val id: String? = null,
    @SerializedName("likes") val likes: List<Like?>? = null,
    @SerializedName("postId") val postId: String? = null,
    @SerializedName("replies") val replies: List<String?>? = null,
    @SerializedName("__v") val v: Int? = null
)

@Serializable
data class Like(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("likedByUserId") val likedByUserId: String? = null,
    @SerializedName("likedByUserName") val likedByUserName: String? = null
)