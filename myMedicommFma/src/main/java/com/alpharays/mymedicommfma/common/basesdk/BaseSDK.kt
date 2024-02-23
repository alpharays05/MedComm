package com.alpharays.mymedicommfma.common.basesdk

import android.content.Context
import com.alpharays.mymedicommfma.community_app.data.di.MedicoCommunityInjector

class BaseSDK {
    companion object {
        private lateinit var context: Context
        fun init(context: Context) {
            this.context = context
        }

        fun getContext(): Context {
            return context
        }

        fun getCommunityInjector(): MedicoCommunityInjector {
            return MedicoCommunityInjector()
        }
    }
}