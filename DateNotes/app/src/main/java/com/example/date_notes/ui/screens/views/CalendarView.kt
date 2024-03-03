package com.example.date_notes.ui.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import com.example.date_notes.ui.compoments.NoteCard
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState

@Composable
fun CalendarView(
    innerPadding: PaddingValues,
    notes: List<Note>,
    updateNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    projects: List<Project>,
    insertReminder: (Reminder) -> Unit,
    updateReminder: (Reminder) -> Unit,
    deleteReminder: (Reminder) -> Unit,
) {
    val calendarState = rememberSelectableCalendarState()
    var selectedNoteId by remember {
        mutableStateOf<Long?>(null)
    }

    Column(
        Modifier.padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SelectableCalendar(
            calendarState = calendarState,
            dayContent = { dayState ->
                DayContent(dayState, notes = notes)
            },
        )
        Spacer(Modifier.height(24.dp))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 32.dp),
        ) {
            items(notes.filter {
                val deadline = it.deadline
                deadline != null && calendarState.selectionState.selection.contains(
                    deadline.toLocalDate()
                )
            }) { note ->
                OutlinedButton(
                    onClick = { selectedNoteId = note.id!! },
                    modifier = Modifier.padding(2.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (note.state == Note.State.Done) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Note state",
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        } else {
                            Spacer(
                                Modifier.width(40.dp)
                            )
                        }
                        Text(
                            text = note.title,
                            modifier = Modifier.weight(1f),
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }
    }

    selectedNoteId?.let { noteId ->
        notes.find { it.id!! == noteId }?.let { note ->
            Dialog(onDismissRequest = { selectedNoteId = null }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = "Edit note", fontSize = 26.sp)
                        Spacer(Modifier.height(12.dp))
                        NoteCard(
                            note = note,
                            updateNote = updateNote,
                            deleteNote = { deleteNote(it); selectedNoteId = null },
                            projects = projects,
                            insertReminder = insertReminder,
                            updateReminder = updateReminder,
                            deleteReminder = deleteReminder,
                            alwaysExpanded = true,
                        )
                        TextButton(onClick = { selectedNoteId = null }) {
                            Text(text = "Close", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : SelectionState> DayContent(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    notes: List<Note>,
) {
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)

    if (notes.map { it.deadline?.toLocalDate() }.contains(date)) {
        Card(
            onClick = {
                selectionState.onDateSelected(date)
            },
            modifier = modifier
                .aspectRatio(1f)
                .padding(3.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor =
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.dayOfMonth.toString(),
                    color = Color.Blue,
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = Color.Gray,
            )
        }
    }
}
