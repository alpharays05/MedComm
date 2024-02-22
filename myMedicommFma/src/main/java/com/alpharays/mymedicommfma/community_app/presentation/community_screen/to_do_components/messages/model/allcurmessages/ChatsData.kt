package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class ChatsData(
    @SerializedName("__v") val v: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("messages") val messages: List<Message>? = null,
    @SerializedName("room") val room: String? = null
)