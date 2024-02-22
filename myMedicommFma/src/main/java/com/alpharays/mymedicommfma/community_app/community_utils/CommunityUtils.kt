package com.alpharays.mymedicommfma.community_app.community_utils

import android.content.Context
import com.alpharays.mymedicommfma.common.basesdk.BaseSDK
import com.alpharays.mymedicommfma.common.nuttiessdk.MedicommConstants
import com.alpharays.mymedicommfma.community_app.data.di.MedicoCommunityInjector


class CommunityUtils {
    companion object {
        private val context = BaseSDK.getContext()

        fun getAuthToken(): String {
            val authTokenSharedPref = context.getSharedPreferences(
                MedicommConstants.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            return authTokenSharedPref.getString(MedicommConstants.AUTH_TOKEN_KEY, null).toString()
        }

        fun setOneTimePostId(postId: String) {
            val postIdSharedPref = context.getSharedPreferences(
                MedicommConstants.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            postIdSharedPref.edit().putString(MedicommConstants.ONE_TIME_POST_ID_KEY, postId).apply()
        }

        fun getOneTimePostId(): String {
            val postIdSharedPref = context.getSharedPreferences(
                MedicommConstants.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            return postIdSharedPref.getString(MedicommConstants.ONE_TIME_POST_ID_KEY, "").toString()
        }
    }
}