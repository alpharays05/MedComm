package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommentsResponse(
    @SerializedName("data") val allComments: List<AllCommentsData?>? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("success") val success: String? = null
)