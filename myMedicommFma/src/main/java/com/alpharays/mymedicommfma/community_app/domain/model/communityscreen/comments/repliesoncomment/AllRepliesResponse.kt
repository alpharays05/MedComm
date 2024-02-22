package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.repliesoncomment


import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllRepliesResponse(
    @SerializedName("data") val allReplies: List<AllCommentsData?>? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("success") val success: String? = null
)