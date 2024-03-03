package com.example.date_notes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.date_notes.model.Reminder
import java.time.LocalDateTime
import java.util.UUID

@Entity(
    "reminder", foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("note_id"),
        onDelete = CASCADE
    )]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Long? = null,

    @ColumnInfo(name = "worker_id")
    override var workerId: UUID?,

    @ColumnInfo(name = "note_id", index = true)
    override var noteId: Long,

    @ColumnInfo(name = "date_time")
    override var dateTime: LocalDateTime,

    @ColumnInfo(name = "text")
    override var text: String?,
) : Reminder {
    constructor(reminder: Reminder) : this(
        id = reminder.id,
        workerId = reminder.workerId,
        noteId = reminder.noteId,
        dateTime = reminder.dateTime,
        text = reminder.text
    )
}
