package com.alpharays.mymedicommfma.communityv2

import android.content.Context

object MedCommRouter {
    const val BASE_URL = "https://medico-ny1q.onrender.com/"
    const val APP_TAG = "APP_TAG"
    const val APP_TAG_ERROR = "APP_TAG_ERROR"
    const val AUTH_TOKEN_SHARED_PREF = "authTokenSharedPrefHighPriority"
    const val AUTH_TOKEN_KEY = "authToken"
    const val ONE_TIME_POST_ID = "one_time_current_post_id_shared_pref"
    const val ONE_TIME_POST_ID_KEY = "one_time_current_post_id_shared_pref_key_value"


    lateinit var context: Context


    fun initiateMedCommRouter(context: Context){
        this.context = context
    }
}