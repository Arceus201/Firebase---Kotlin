package com.example.firebase___kotlin.fcm

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

    private fun sendNotification(strTitle: String, strMessage: String) {
        val intent = Intent("OPEN_ACTIVITY_FROM_NOTIFICATION")
        intent.putExtra("abc","abc")
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val stackBuilder = TaskStackBuilder.create(this)

// Add the intent, which inflates the back stack.
//        stackBuilder.addNextIntentWithParentStack(intent)

// Get the PendingIntent containing the entire back stack.
//        val pendingIntent: PendingIntent? = stackBuilder.getPendingIntent(
//            0,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
        val pendingIntent =
            PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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