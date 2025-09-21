package com.example.remind

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val noteText = intent.getStringExtra("noteText") ?: "Reminder"

        val channelId = "memo_reminder_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Reminder")
            .setContentText(noteText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Safe notification ID using lower 28 bits of currentTimeMillis
        val notificationId = (System.currentTimeMillis() and 0x0FFFFFFF).toInt()
        notificationManager.notify(notificationId, notification)
    }
}
