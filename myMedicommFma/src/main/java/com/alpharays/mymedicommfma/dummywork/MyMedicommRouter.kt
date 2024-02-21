package com.alpharays.mymedicommfma.dummywork

import android.app.Activity
import android.content.Context
import android.content.Intent

object MyMedicommRouter {
    fun startDummyActivity(context: Context) {
        // Start the DummyActivity
        val intent = Intent(context, DummyActivity::class.java)
        context.startActivity(intent)
    }
}