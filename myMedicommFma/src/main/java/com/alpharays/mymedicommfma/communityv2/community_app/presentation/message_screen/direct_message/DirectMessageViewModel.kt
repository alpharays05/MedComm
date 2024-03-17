package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message

import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.DirectMessage
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.usecase.MessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectMessageViewModel @Inject constructor(
    context: Context,
    private val socketIO: SocketIO,
    private val useCase: MessagesUseCase,
) : ViewModel() {
    var token = ""

    init {
        connectToJoinRoomSocket()
        connectToMessagesSocket()
        token = CommunityUtils.getAuthToken(context)
    }

    private fun getAllMessages(chatId: String) {
        viewModelScope.launch {
            useCase(token = token, allChats = AllChats(chatId))
        }
    }

    fun sendMessage(messageData: SnapshotStateMap<String, DirectMessage>) {
        socketIO.emitDirectMessage(messageData.toMap())
    }

    private fun connectToMessagesSocket() {
        socketIO.connectMessagesSocket()
    }

    private fun disConnectMessagesSocket() {
        socketIO.disconnectMessagesSocket()
    }

    private fun connectToJoinRoomSocket() {
        socketIO.connectJoinRoomSocket()
    }

    private fun disConnectJoinRoomSocket() {
        socketIO.disconnectJoinRoomSocket()
    }

    override fun onCleared() {
        super.onCleared()
        disConnectMessagesSocket()
    }

}