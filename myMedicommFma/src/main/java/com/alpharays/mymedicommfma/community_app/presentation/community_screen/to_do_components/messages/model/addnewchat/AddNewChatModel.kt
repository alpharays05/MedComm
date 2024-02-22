package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddNewChatModel(
    @SerializedName("data") val chatInfo: ChatInfo? = null,
    val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    val success: String? = null
)