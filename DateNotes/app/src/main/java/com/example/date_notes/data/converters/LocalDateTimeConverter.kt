package com.example.date_notes.data.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

object LocalDateTimeConverter {
    @TypeConverter
    fun fromSeconds(seconds: Long?): LocalDateTime? {
        return seconds?.let { LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun toSeconds(dateTime: LocalDateTime?): Long? {
        return dateTime?.atZone(ZoneOffset.UTC)?.toEpochSecond()
    }
}
