package com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.userposts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MyCommunityPostsParent(
    @SerializedName("success") val success: Int? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("data") val communityPostsData: List<MyCommunityPosts>? = null,
)