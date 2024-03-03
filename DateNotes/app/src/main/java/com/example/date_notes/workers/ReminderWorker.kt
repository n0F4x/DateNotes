package com.example.date_notes.workers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.date_notes.MainActivity
import com.example.date_notes.MainApplication
import com.example.date_notes.R

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private val notificationId = 42

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val message = inputData.getString(textKey) ?: "You have been reminded"

        val builder = NotificationCompat.Builder(applicationContext, MainApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("DateNotes")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            val notification = builder.build()
            if (this@ReminderWorker.isStopped) {
                return@doWork Result.success()
            }
            notify(notificationId, notification)
        }

        return Result.success()
    }

    companion object {
        const val textKey = "MESSAGE"
    }
}
