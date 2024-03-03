package com.example.date_notes.ui.state

import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import java.time.LocalDateTime

data class NoteState(
    override val id: Long? = null,
    override var title: String,
    override var description: String? = null,
    override var state: Note.State = Note.State.TODO,
    override var deadline: LocalDateTime? = null,
    override var activationDateTime: LocalDateTime? = null,
    override val reminders: List<Reminder> = emptyList(),
    override var project: Project? = null,
) : Note {
    constructor(note: Note) : this(
        id = note.id,
        note.title,
        note.description,
        note.state,
        note.deadline,
        note.activationDateTime,
        project = note.project
    )
}
