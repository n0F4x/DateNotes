package com.example.date_notes.ui.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import com.example.date_notes.ui.compoments.NoteCard

@Composable
fun ListView(
    innerPadding: PaddingValues,
    notes: List<Note>,
    updateNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    projects: List<Project>,
    insertReminder: (Reminder) -> Unit,
    updateReminder: (Reminder) -> Unit,
    deleteReminder: (Reminder) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        items(notes, { it.id!! }) {
            NoteCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                note = it,
                updateNote = updateNote,
                deleteNote = deleteNote,
                projects = projects,
                insertReminder = insertReminder,
                updateReminder = updateReminder,
                deleteReminder = deleteReminder,
            )
        }
    }
}
