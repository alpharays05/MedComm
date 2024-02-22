package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommentsRequestBody(
    @SerializedName("postId") val postId: String? = null
)