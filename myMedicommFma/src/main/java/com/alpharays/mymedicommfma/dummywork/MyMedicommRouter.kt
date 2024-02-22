package com.alpharays.mymedicommfma.dummywork

import android.content.Context
import android.content.Intent
import com.alpharays.mymedicommfma.common.basesdk.BaseSDK

object MyMedicommRouter {
    fun startDummyActivity(context: Context) {
        // Start the DummyActivity
        val intent = Intent(context, DummyActivity::class.java)
        context.startActivity(intent)
    }

    fun initSDK(context: Context) {
        BaseSDK.init(context)
    }
}