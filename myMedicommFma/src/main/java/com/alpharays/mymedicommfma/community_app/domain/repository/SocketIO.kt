package com.alpharays.mymedicommfma.community_app.domain.repository

import com.alpharays.mymedicommfma.community_app.domain.model.socketio.MessagesSocketState
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.DirectMessage
import kotlinx.coroutines.flow.StateFlow

interface SocketIO{
    val messagesSocketState: StateFlow<MessagesSocketState>
    val inboxSocketState: StateFlow<MessagesSocketState>
    val joinRoomSocketState: StateFlow<MessagesSocketState>
    val messageRoomSocketState: StateFlow<MessagesSocketState>

    // inbox screen socket handler
    fun connectInboxSocket()
    fun disconnectInboxSocket()


    // direct message screen socket handler
    fun connectJoinRoomSocket()
    fun disconnectJoinRoomSocket()

    // message room socket handler
    fun connectMessagesRoomSocket()
    fun disconnectMessagesRoomSocket()


    // reply event socket handler
    fun connectMessagesSocket()
    fun disconnectMessagesSocket()

    fun emitDirectMessage(newMessageMap: Map<String, DirectMessage>)

}