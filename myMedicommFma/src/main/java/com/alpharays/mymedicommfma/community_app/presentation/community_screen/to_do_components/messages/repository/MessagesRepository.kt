package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.repository

import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel
import com.alpharays.mymedicommfma.medico_utils.ResponseResult

interface MessagesRepository {
    suspend fun startNewChat(token: String, newChat: NewChat): ResponseResult<AddNewChatModel>
    suspend fun getAllInboxMessagesList(token: String): ResponseResult<InboxMsgModel>
    suspend fun getAllMessagesList(token: String, allChats: AllChats): ResponseResult<AllCurrMessages>
}