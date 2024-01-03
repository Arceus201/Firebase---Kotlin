package com.example.firebase___kotlin.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.firebase___kotlin.MainActivity
import com.example.firebase___kotlin.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = MyFirebaseMessagingService::class.java.name

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Nhận dữ liệu từ Firebase
//        val notification = message.notification
//        if (notification == null) {
//            return
//        }
//        val strTitle = notification.title
//        val strMessage = notification.body

        // Dữ liệu từ message
        val stringMap: Map<String, String> = message.data

        val dataTitle = stringMap["user_name"]
        val dataMessage = stringMap["description"]

        // Gửi thông báo
        sendNotification(dataTitle ?: "strTitle", dataMessage ?: "strMessage")
    }

    private fun sendNotification(strTitle: String, strMessage: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle(strTitle)
            .setContentText(strMessage)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        val notification: Notification = notificationBuilder.build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e(TAG, "Token generated: $token")
    }
}