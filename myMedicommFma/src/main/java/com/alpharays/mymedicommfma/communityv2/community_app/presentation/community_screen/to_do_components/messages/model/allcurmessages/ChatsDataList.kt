package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChatsDataList(
    @SerializedName("chatsdata") val chatsData: List<ChatsData>? = null,
    val id: String? = null
)