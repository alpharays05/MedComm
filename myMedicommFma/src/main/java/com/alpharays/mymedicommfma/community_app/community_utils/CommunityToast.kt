package com.alpharays.mymedicommfma.community_app.community_utils

import android.content.Context
import android.widget.Toast

class CommunityToast {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}