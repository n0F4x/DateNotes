package com.example.date_notes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.date_notes.model.Note
import java.time.LocalDateTime

@Entity("note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "state")
    var state: Note.State,

    @ColumnInfo(name = "deadline")
    var deadline: LocalDateTime?,

    @ColumnInfo(name = "activation_date_time")
    var activationDateTime: LocalDateTime?,

    @ColumnInfo(name = "project_id")
    var projectId: Long?
) {
    constructor(note: Note) : this(
        id = note.id,
        note.title,
        note.description,
        note.state,
        note.deadline,
        note.activationDateTime,
        projectId = note.project?.id
    )
}
