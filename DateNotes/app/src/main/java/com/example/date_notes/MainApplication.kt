package com.example.date_notes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.date_notes.data.AppDatabase
import com.example.date_notes.data.AppRepository

class MainApplication : Application() {
    private val _db by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        AppRepository(_db.noteDao(), _db.reminderDao(), _db.projectDao())
    }

    override fun onCreate() {
        super.onCreate()
        val name = "reminder_channel"
        val descriptionText = "reminder_reminder"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "reminder_id"
    }
}
