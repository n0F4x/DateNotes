package com.example.date_notes.data.converters

import androidx.room.TypeConverter
import com.example.date_notes.model.Note

object NoteStateConverter {
    @TypeConverter
    fun fromCode(code: Int): Note.State? {
        return enumValues<Note.State>().firstOrNull { it.ordinal == code }
    }

    @TypeConverter
    fun toCode(state: Note.State): Int {
        return state.ordinal
    }
}
