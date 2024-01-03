package com.example.firebase___kotlin.fcm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

// Tạo channel_id
class MyApplication : Application() {
    companion object {
        const val CHANNEL_ID = "push_notification_id"
    }

    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "PushNotification", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}