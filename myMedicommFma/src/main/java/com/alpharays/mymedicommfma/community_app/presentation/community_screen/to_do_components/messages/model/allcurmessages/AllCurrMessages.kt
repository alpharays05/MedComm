package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AllCurrMessages(
    @SerializedName("data") val chatsDataList: ChatsDataList? = null,
    val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    val success: String? = null
)