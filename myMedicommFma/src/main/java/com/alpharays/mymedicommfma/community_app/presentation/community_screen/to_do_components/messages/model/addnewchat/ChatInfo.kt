package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChatInfo(
    @SerializedName("__v") val v: String? = null,
    @SerializedName("_id") val id: String? = null,
    val createdAt: String? = null,
    val messages: List<String>? = null,
    val participants: List<String>? = null,
    val room: String? = null
)