package com.app.namllahuser.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.namllahuser.R
import com.app.namllahuser.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.subjects.PublishSubject
import java.util.*

class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    companion object{
        private var tokenData: PublishSubject<String> = PublishSubject.create()

        fun getObservable(): PublishSubject<String> {
            return tokenData
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.v("ttt",p0)
        tokenData.onNext(p0)
    }
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.v("ttt","new Message")


        // Check if message contains a data payload.
        if (p0.getData().size > 0) {
            Log.d(
                "ttt",
                "Message data payload: " + p0.getData()
            )
            if ( /* Check if data needs to be processed by long running job */true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.

        // Check if message contains a notification payload.
        if (p0.getNotification() != null) {
            Log.d(
                "ttt",
                "Message Notification Body: " + p0.getNotification()!!.getBody()
            )
            tokenData.onNext(p0.getNotification()!!.getBody()!!)
            sendNotification(
                p0.getNotification()!!.getTitle()!!,
                p0.getNotification()!!.getBody()!!
            )
        }

    }

    private fun sendNotification(
        messageTitle: String,
        messageBody: String
    ) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_NO_CREATE
        )
        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setContentIntent(pendingIntent)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
    private fun handleNow() {
        Log.d("ttt", "Short lived task is done.");
    }

}