package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.repository

import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseHandler
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val apiServices: CommunityApiServices,
    private val responseHandler: ResponseHandler,
//    private val medicoDao: MedicoDao,
) : MessagesRepository {
    override suspend fun startNewChat(
        token: String,
        newChat: NewChat,
    ): ResponseResult<AddNewChatModel> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        apiServices.startNewChat(token, newChat)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllInboxMessagesList(token: String): ResponseResult<InboxMsgModel> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        apiServices.getAllInboxMessagesList(token)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllMessagesList(
        token: String,
        allChats: AllChats,
    ): ResponseResult<AllCurrMessages> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        apiServices.getAllMessagesList(token, allChats)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }
}