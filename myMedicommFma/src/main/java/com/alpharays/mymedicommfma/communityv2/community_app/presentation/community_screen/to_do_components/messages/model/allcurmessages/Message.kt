package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerializedName("_id") val id: String? = null,
    val messageContent: String? = null,
    val senderId: String? = null,
    val sentTime: String? = null
)