package com.example.smartbottle

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.smartbottle.core.domain.CoreRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class PushNotificationService: FirebaseMessagingService() {

    private val coreRepository: CoreRepository by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //update server, send token to server
        CoroutineScope(Dispatchers.IO).launch {
            coreRepository.postFcmToken(token)
        }
        Log.d("PushNotification: token renewed",token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.notification?.let { notification ->
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 채널 생성 (Android 8.0 이상 필수)
            val channel = NotificationChannel(
                "default",
                "기본 채널",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)

            val notificationBuilder = NotificationCompat.Builder(this, "default")
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)

            notificationManager.notify(0, notificationBuilder.build())
        }

        coreRepository.sendCommandToDevice("BUZZ_ON\n")
    }

}