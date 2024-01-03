package com.example.firebase___kotlin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Nhận được FCM registration token mới
                val token = task.result

                // Log và hiển thị toast
                Log.e(TAG, "Token in main: $token")
//                val msg = getString(R.string.msg_token_fmt, token)
//                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            })
    }
}
