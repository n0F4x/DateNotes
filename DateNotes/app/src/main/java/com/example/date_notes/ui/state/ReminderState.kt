package com.example.date_notes.ui.state

import com.example.date_notes.model.Reminder
import java.time.LocalDateTime
import java.util.UUID

data class ReminderState(
    override var id: Long? = null,
    override var workerId: UUID? = null,
    override val noteId: Long,
    override var dateTime: LocalDateTime,
    override var text: String? = null,
) : Reminder {
    constructor(reminder: Reminder) : this(
        id = reminder.id,
        workerId = reminder.workerId,
        noteId = reminder.noteId,
        dateTime = reminder.dateTime,
        text = reminder.text,
    )
}
