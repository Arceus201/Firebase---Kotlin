package com.example.firebase___kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.firebase___kotlin.tmp.TmpActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("NotificationReceiver", "Received broadcast")
        // Xử lý khi nhấn vào thông báo
        if(intent?.action == "OPEN_ACTIVITY_FROM_NOTIFICATION"){
            Log.e("intent action ", "OPEN_ACTIVITY_FROM_NOTIFICATION")
            val resultIntent = Intent(context, TmpActivity::class.java)
            resultIntent.putExtra("abc", "abccccccccccccccccccccc")
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(resultIntent)
        }

    }
}