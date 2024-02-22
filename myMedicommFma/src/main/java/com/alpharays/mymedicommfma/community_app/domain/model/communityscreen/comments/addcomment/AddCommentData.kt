package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.addcomment


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentData(
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("postId") val postId: String? = null
)