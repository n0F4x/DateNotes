package com.example.date_notes.data.converters

import androidx.room.TypeConverter
import java.util.UUID

object UUIDConverter {
    @TypeConverter
    fun fromString(string: String?): UUID? {
        if (string != null) {
            return UUID.fromString(string)
        }
        return null
    }

    @TypeConverter
    fun toString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}
