package com.example.date_notes.model

import java.time.LocalDateTime

interface Note {
    val id: Long?
    val title: String
    val description: String?
    val state: State
    val deadline: LocalDateTime?
    val activationDateTime: LocalDateTime?
    val reminders: List<Reminder>
    val project: Project?

    enum class State {
        Done,
        TODO
    }
}
