package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.repliesoncomment


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllRepliesData(
    @SerializedName("commentId") val commentId: String? = null
)