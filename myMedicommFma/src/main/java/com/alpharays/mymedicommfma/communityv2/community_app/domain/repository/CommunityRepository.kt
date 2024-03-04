package com.alpharays.mymedicommfma.communityv2.community_app.domain.repository

import com.alpharays.medico.medico_utils.ResponseResult
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

interface CommunityRepository {
    suspend fun getAllPostsList(token: String): ResponseResult<AllCommunityPostsParent>
//    suspend fun getCurrentUserPostsList(docId: String): ResponseResult<MyCommunityPostsParent>
    suspend fun addNewPost(token: String, addNewCommunityPost: AddNewCommunityPost): ResponseResult<NewPostResponse>
    suspend fun getAllComments(allCommentsRequestBody: AllCommentsRequestBody, token: String): ResponseResult<AllCommentsResponse>
    suspend fun getAllReplies(commentId: GetAllRepliesData, token: String): ResponseResult<AllRepliesResponse>
    suspend fun addNewComment(addCommentData: AddCommentData, token: String): ResponseResult<AddCommentResponse>
    suspend fun updateComment(reply: UpdateCommentData, token: String): ResponseResult<UpdatedCommentResponse>
}