package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.updatecomment


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCommentData(
    @SerializedName("commentId") val commentId: String? = null,
    @SerializedName("reply") val reply: String? = null
)