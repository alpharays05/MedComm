package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox

import androidx.lifecycle.ViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
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