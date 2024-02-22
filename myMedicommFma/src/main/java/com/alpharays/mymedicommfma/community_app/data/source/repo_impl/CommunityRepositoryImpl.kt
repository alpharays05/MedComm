package com.alpharays.mymedicommfma.community_app.data.source.repo_impl

import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants.SOMETHING_WENT_WRONG
import com.alpharays.mymedicommfma.common.nuttiessdk.ResponseHandler
import com.alpharays.mymedicommfma.common.nuttiessdk.ResponseResult
import com.alpharays.mymedicommfma.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.allposts.AllCommunityPostsParent
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.addcomment.AddCommentData
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.addcomment.AddCommentResponse
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsRequestBody
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.allcomments.AllCommentsResponse
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.repliesoncomment.AllRepliesResponse
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.repliesoncomment.GetAllRepliesData
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.updatecomment.UpdateCommentData
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.comments.updatecomment.UpdatedCommentResponse
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.community_app.domain.model.communityscreen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.community_app.domain.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommunityRepositoryImpl(
    private val apiServices: CommunityApiServices,
    private val responseHandler: ResponseHandler,
) : CommunityRepository {
    override suspend fun getAllPostsList(token: String): ResponseResult<AllCommunityPostsParent> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        apiServices.getAllCommunityPosts(token)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

//    override suspend fun getCurrentUserPostsList(docId: String): ResponseResult<MyCommunityPostsParent> =
//        withContext(Dispatchers.IO) {
//            return@withContext try {
//                responseHandler.callAPI {
//                    withContext(Dispatchers.IO) {
//                        apiServices.getMyAllPosts(docId)
//                    }
//                }
//            } catch (e: Exception) {
//                ResponseResult.Error(SOMETHING_WENT_WRONG)
//            }
//        }

    override suspend fun addNewPost(
        token: String,
        addNewCommunityPost: AddNewCommunityPost,
    ): ResponseResult<NewPostResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        apiServices.addMyNewPost(addNewCommunityPost, token)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllComments(
        allCommentsRequestBody: AllCommentsRequestBody,
        token: String,
    ): ResponseResult<AllCommentsResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.getAllComments(allCommentsRequestBody, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllReplies(
        commentId: GetAllRepliesData,
        token: String,
    ): ResponseResult<AllRepliesResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.getAllReplies(commentId, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun addNewComment(
        addCommentData: AddCommentData,
        token: String,
    ): ResponseResult<AddCommentResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.addNewComment(addCommentData, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun updateComment(
        reply: UpdateCommentData,
        token: String,
    ): ResponseResult<UpdatedCommentResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.updateComment(reply, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }
}