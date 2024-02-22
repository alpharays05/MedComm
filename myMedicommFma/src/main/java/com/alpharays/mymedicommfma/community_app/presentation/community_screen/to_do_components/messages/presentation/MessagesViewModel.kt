package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.common.nuttiessdk.ResponseResult
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.usecase.MessagesUseCase
import kotlinx.coroutines.launch

class MessagesViewModel(private val messagesUseCase: MessagesUseCase) : ViewModel() {
    private val _allInboxMessagesLiveData = MutableLiveData<ResponseResult<InboxMsgModel>>()
    val allInboxMessagesLiveData get() = _allInboxMessagesLiveData

    private val _allMessagesLiveData = MutableLiveData<ResponseResult<AllCurrMessages>>()
    val allMessagesLiveData get() = _allMessagesLiveData

    private val _newChatInitLiveData = MutableLiveData<ResponseResult<AddNewChatModel>>()
    val newChatInitLiveData get() = _newChatInitLiveData

    fun getAllInboxMessages(token: String) {
        _allInboxMessagesLiveData.postValue(ResponseResult.Loading())
        viewModelScope.launch {
            _allInboxMessagesLiveData.postValue(messagesUseCase.invoke(token))
        }
    }

    fun getAllCurMessages(token: String, allChats: AllChats) {
        _allMessagesLiveData.postValue(ResponseResult.Loading())
        viewModelScope.launch {
            _allMessagesLiveData.postValue(messagesUseCase.invoke(token, allChats))
        }
    }

    fun getNewChatInitiated(token: String, newChat: NewChat) {
        _newChatInitLiveData.postValue(ResponseResult.Loading())
        viewModelScope.launch {
            _newChatInitLiveData.postValue(messagesUseCase.invoke(token, newChat))
        }
    }
}