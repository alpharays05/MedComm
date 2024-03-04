package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.addcomment


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentResponse(
    @SerializedName("data") val newCommentData: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("success") val success: String? = null
)