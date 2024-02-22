package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages

import kotlinx.serialization.Serializable

@Serializable
data class ChatMsg(
    val chatId: String? = null,
    val createdAt: String? = null,
    val lastMessage: String? = null,
    val lastMsgTimeStamp: String? = null,
    val room: String? = null
)