package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.repository

import com.alpharays.medico.medico_utils.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel

interface MessagesRepository {
    suspend fun startNewChat(token: String, newChat: NewChat): ResponseResult<AddNewChatModel>
    suspend fun getAllInboxMessagesList(token: String): ResponseResult<InboxMsgModel>
    suspend fun getAllMessagesList(token: String, allChats: AllChats): ResponseResult<AllCurrMessages>
}