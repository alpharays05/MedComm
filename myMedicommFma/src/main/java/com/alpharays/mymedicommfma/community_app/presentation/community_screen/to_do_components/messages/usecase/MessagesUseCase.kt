package com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.usecase

import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel
import com.alpharays.mymedicommfma.community_app.presentation.community_screen.to_do_components.messages.repository.MessagesRepository
import com.alpharays.mymedicommfma.medico_utils.MedicommConstants.SOMETHING_WENT_WRONG
import com.alpharays.mymedicommfma.medico_utils.ResponseResult

class MessagesUseCase(
    private val messagesRepository: MessagesRepository
) {
    suspend operator fun invoke(token: String, newChat: NewChat): ResponseResult<AddNewChatModel> {
        return try {
            val startNewChatResponse = messagesRepository.startNewChat(token, newChat)
            startNewChatResponse
        } catch (e: Exception) {
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
    }

    suspend operator fun invoke(token: String, allChats: AllChats): ResponseResult<AllCurrMessages> {
        return try {
            val myCurrMessages = messagesRepository.getAllMessagesList(token, allChats)
            myCurrMessages
        } catch (e: Exception) {
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
    }

    suspend operator fun invoke(token: String): ResponseResult<InboxMsgModel> {
        return try {
            val myInboxMessages = messagesRepository.getAllInboxMessagesList(token)
            myInboxMessages
        } catch (e: Exception) {
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
    }
}