package com.alpharays.mymedicommfma.community_app.presentation.message_screen.message_inbox

import androidx.lifecycle.ViewModel
import com.alpharays.mymedicommfma.community_app.domain.repository.SocketIO

class InboxViewModel(
    private val socketIO: SocketIO,
) : ViewModel() {
    init {
        connectInboxSocket()
    }

    private fun connectInboxSocket() {
        socketIO.connectInboxSocket()
    }

    private fun disconnectInboxSocket() {
        socketIO.disconnectInboxSocket()
    }

    override fun onCleared() {
        super.onCleared()
        disconnectInboxSocket()
    }
}