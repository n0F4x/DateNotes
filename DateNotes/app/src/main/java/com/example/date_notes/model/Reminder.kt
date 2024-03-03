package com.example.date_notes.model

import java.time.LocalDateTime
import java.util.UUID

interface Reminder {
    val id: Long?
    val workerId: UUID?
    val noteId: Long
    val dateTime: LocalDateTime
    val text: String?
}
