package com.example.firebase___kotlin.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getBroadcast
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.firebase___kotlin.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context
import com.example.firebase___kotlin.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = message.notification ?: return
        val strTitle = notification.title
        val strMessage = notification.body


        sendNotification(strTitle ?: "strTitle", strMessage ?: "strMessage")
    }

    @SuppressLint("LaunchActivityFromNotification", "UnspecifiedImmutableFlag")
    private fun sendNotification(strTitle: String, strMessage: String) {
        val intent = Intent("OPEN_ACTIVITY_FROM_NOTIFICATION")
        intent.putExtra("abc","abc")
        val pendingIntent =
            getBroadcast(
                this, 0, intent,
                 PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
            )

        val notificationBuilder = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle(strTitle)
            .setContentText(strMessage)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification: Notification = notificationBuilder.build()
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.notify(1, notification)
    }
}