package com.example.date_notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.date_notes.data.converters.LocalDateTimeConverter
import com.example.date_notes.data.converters.NoteStateConverter
import com.example.date_notes.data.converters.UUIDConverter
import com.example.date_notes.data.dao.NoteDao
import com.example.date_notes.data.dao.ProjectDao
import com.example.date_notes.data.dao.ReminderDao
import com.example.date_notes.data.entities.NoteEntity
import com.example.date_notes.data.entities.ProjectEntity
import com.example.date_notes.data.entities.ReminderEntity

@Database(
    entities = [NoteEntity::class, ReminderEntity::class, ProjectEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [LocalDateTimeConverter::class, NoteStateConverter::class, UUIDConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun reminderDao(): ReminderDao
    abstract fun projectDao(): ProjectDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(applicationContext: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "main"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
