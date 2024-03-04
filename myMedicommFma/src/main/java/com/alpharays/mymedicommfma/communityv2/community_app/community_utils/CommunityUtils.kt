package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import android.content.Context
import com.alpharays.medico.MedicoApp
import com.alpharays.medico.medico_utils.MedicoConstants
import com.alpharays.medico.medico_utils.MedicoUtils

class CommunityUtils {
    companion object {
        val context = MedicoApp.getInstance()
        fun setAuthToken(token: String) {
            val authTokenSharedPref = MedicoUtils.context.getSharedPreferences(
                MedicoConstants.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            authTokenSharedPref.edit().putString(MedicoConstants.AUTH_TOKEN_KEY, token).apply()
        }

        fun getAuthToken(): String {
            val authTokenSharedPref = MedicoUtils.context.getSharedPreferences(
                MedicoConstants.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            return authTokenSharedPref.getString(MedicoConstants.AUTH_TOKEN_KEY, null).toString()
        }

        fun setOneTimePostId(postId: String) {
            val postIdSharedPref = context.getSharedPreferences(
                MedicoConstants.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            postIdSharedPref.edit().putString(MedicoConstants.ONE_TIME_POST_ID_KEY, postId).apply()
        }

        fun getOneTimePostId(): String {
            val postIdSharedPref = context.getSharedPreferences(
                MedicoConstants.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            return postIdSharedPref.getString(MedicoConstants.ONE_TIME_POST_ID_KEY, "").toString()
        }
    }
}