package com.app.namllahuser.presentation

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.namllahuser.R

class MyForgroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val input = intent?.getStringExtra(Constants.inputExtra)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
//
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.timeIsRunning))
            .setContentText(getString(R.string.timerDescription))
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
// 2
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val channel = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

}