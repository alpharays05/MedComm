package com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote

import com.alpharays.medico.medico_utils.MedicoConstants
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_CHAT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_POST
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ALL_COMMUNITY_POSTS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_CHATS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_COMMENTS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_REPLIES_ON_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_INBOX_MESSAGES
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.UPDATE_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.AllCommunityPostsParent
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.addcomment.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.addcomment.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.repliesoncomment.AllRepliesResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.repliesoncomment.GetAllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.updatecomment.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.updatecomment.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.AllChats
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.NewChat
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.addnewchat.AddNewChatModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allcurmessages.AllCurrMessages
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components.messages.model.allinboxmessages.InboxMsgModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface CommunityApiServices {
    /**
     * community screen endpoints
     */

    // all docs posts
    @GET(ALL_COMMUNITY_POSTS)
    suspend fun getAllCommunityPosts(
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<AllCommunityPostsParent>

    // all new post
    @POST(ADD_NEW_POST)
    suspend fun addMyNewPost(
        @Body addNewCommunityPost: AddNewCommunityPost,
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<NewPostResponse>

    // all comments
    @PUT(GET_ALL_COMMENTS)
    suspend fun getAllComments(
        @Body postIdBody: AllCommentsRequestBody,
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<AllCommentsResponse>

    // all replies on a comment
    @GET(GET_ALL_REPLIES_ON_COMMENT)
    suspend fun getAllReplies(
        @Body commentId: GetAllRepliesData,
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<AllRepliesResponse>

    // add new comment
    @POST(ADD_COMMENT)
    suspend fun addNewComment(
        @Body addCommentData: AddCommentData,
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<AddCommentResponse>

    // reply on a comment
    @PUT(UPDATE_COMMENT)
    suspend fun updateComment(
        @Body reply: UpdateCommentData,
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<UpdatedCommentResponse>


    /**
     * message screen endpoints
     */

    // add new chat
    @POST(ADD_NEW_CHAT)
    suspend fun startNewChat(
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
        @Body newChat: NewChat,
    ): Response<AddNewChatModel>

    // get all inbox messages
    @GET(GET_INBOX_MESSAGES)
    suspend fun getAllInboxMessagesList(
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
    ): Response<InboxMsgModel>

    // get all chats
    @GET(GET_ALL_CHATS)
    suspend fun getAllMessagesList(
        @Header(MedicoConstants.TOKEN_KEYWORD) token: String,
        @Body allChats: AllChats,
    ): Response<AllCurrMessages>
}