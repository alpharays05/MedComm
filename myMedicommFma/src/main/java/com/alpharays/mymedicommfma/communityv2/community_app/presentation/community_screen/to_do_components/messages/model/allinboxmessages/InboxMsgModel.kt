package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class InboxMsgModel(
    @SerializedName("data") val chatsList: List<ChatMsg>? = null,
    val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    val success: String? = null
)