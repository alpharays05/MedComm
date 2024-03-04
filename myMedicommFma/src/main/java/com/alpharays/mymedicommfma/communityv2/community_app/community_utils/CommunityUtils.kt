package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import android.content.Context
import com.alpharays.mymedicommfma.communityv2.MedCommRouter

class CommunityUtils {
    companion object {
        val context = MedCommRouter.context
        fun setAuthToken(token: String) {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            authTokenSharedPref.edit().putString(MedCommRouter.AUTH_TOKEN_KEY, token).apply()
        }

        fun getAuthToken(): String {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            return authTokenSharedPref.getString(MedCommRouter.AUTH_TOKEN_KEY, null).toString()
        }

        fun setOneTimePostId(postId: String) {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            postIdSharedPref.edit().putString(MedCommRouter.ONE_TIME_POST_ID_KEY, postId).apply()
        }

        fun getOneTimePostId(): String {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            return postIdSharedPref.getString(MedCommRouter.ONE_TIME_POST_ID_KEY, "").toString()
        }
    }
}