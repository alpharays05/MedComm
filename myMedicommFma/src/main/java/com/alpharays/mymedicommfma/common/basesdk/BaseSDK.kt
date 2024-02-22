package com.alpharays.mymedicommfma.common.basesdk

import android.content.Context

class BaseSDK {
    companion object {
        private lateinit var context: Context
        fun init(context: Context) {
            this.context = context
        }

        fun getContext(): Context {
            return context
        }
    }
}