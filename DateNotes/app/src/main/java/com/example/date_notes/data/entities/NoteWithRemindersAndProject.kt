package com.example.date_notes.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import java.time.LocalDateTime

class NoteWithRemindersAndProject (
    @Embedded
    private val _note: NoteEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    override val reminders: List<ReminderEntity>,

    @Relation(
        parentColumn = "project_id",
        entityColumn = "id"
    )
    override val project: ProjectEntity?,
) : Note {
    override val id: Long?
        get() = _note.id
    override val title: String
        get() = _note.title
    override val description: String?
        get() = _note.description
    override val state: Note.State
        get() = _note.state
    override val deadline: LocalDateTime?
        get() = _note.deadline
    override val activationDateTime: LocalDateTime?
        get() = _note.activationDateTime
}
