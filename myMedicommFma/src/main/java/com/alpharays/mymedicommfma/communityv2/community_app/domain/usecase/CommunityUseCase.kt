package com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase

import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.allposts.AllCommunityPostsParent
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.communityscreen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityUseCase @Inject constructor(
    private val communityRepository: CommunityRepository,
) {
    operator fun invoke(token: String): Flow<ResponseResult<AllCommunityPostsParent>> = flow {
        emit(ResponseResult.Loading())
        val response = try {
            val allPostsList = communityRepository.getAllPostsList(token)
            allPostsList
        } catch (e: Exception) {
            println(e.printStackTrace())
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
        emit(response)
    }

    operator fun invoke(
        token: String,
        addNewCommunityPost: AddNewCommunityPost,
    ): Flow<ResponseResult<NewPostResponse>> = flow {
        val response = try {
            val response = communityRepository.addNewPost(token, addNewCommunityPost)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
        emit(response)
    }

    operator fun invoke(
        token: String,
        postId: AllCommentsRequestBody,
    ): Flow<ResponseResult<AllCommentsResponse>> = flow {
        val response = try {
            val response = communityRepository.getAllComments(postId, token)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
        emit(response)
    }
}