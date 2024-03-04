package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Messages(
    val avatar: String? = null,
    val id: String? = null,
    val name: String? = null,
    val message: String? = null,
    val messageDate: String? = null
)


@Serializable
data class DirectMessage(
    var message: String? = null,
    var senderId: String? = null,
    val senderNumber: String? = null,
    val receiverNumber: String? = null
)

@Serializable
data class NewChat(
    val sid: String? = null
)

@Serializable
data class AllChats(
    @SerializedName("chatid") val chatId: String? = null
)