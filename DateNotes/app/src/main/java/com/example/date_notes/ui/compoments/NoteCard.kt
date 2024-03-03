package com.example.date_notes.ui.compoments

import android.view.KeyEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import com.example.date_notes.ui.state.NoteState
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    updateNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit,
    projects: List<Project>,
    insertReminder: (Reminder) -> Unit,
    updateReminder: (Reminder) -> Unit,
    deleteReminder: (Reminder) -> Unit,
    alwaysExpanded: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var expanded by remember { mutableStateOf(false) }
    var showProjectSelector by remember {
        mutableStateOf(false)
    }
    var showEditDeadlineDialog by remember {
        mutableStateOf(false)
    }
    var showSetActivationDateDialog by remember {
        mutableStateOf(false)
    }

    OutlinedCard(
        onClick = { expanded = !expanded },
        modifier = modifier,
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SuggestionChip(
                            onClick = {
                                showProjectSelector = true
                            },
                            label = {
                                Text(
                                    text = note.project?.name ?: "None"
                                )
                            },
                            modifier = Modifier
                                .height(20.dp)
                                .weight(1f),
                        )
                        Spacer(Modifier.width(8.dp))
                        SuggestionChip(
                            onClick = {
                                /* TODO */
                                showEditDeadlineDialog = true
                            },
                            label = {
                                Text(text = "Deadline: ${note.deadline?.format(dateTimeFormatter) ?: "-"}")
                            },
                            modifier = Modifier
                                .height(20.dp)
                                .weight(2f),
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.width(4.dp))
                        Checkbox(
                            checked = note.state == Note.State.Done,
                            onCheckedChange = {
                                val temp = NoteState(note)
                                temp.state = if (it) {
                                    Note.State.Done
                                } else {
                                    Note.State.TODO
                                }
                                updateNote(temp)
                            },
                            modifier = Modifier.size(12.dp),
                        )
                        Spacer(Modifier.width(16.dp))
                        var title by remember {
                            mutableStateOf(note.title)
                        }
                        BasicTextField(
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            modifier = Modifier
                                .onKeyEvent {
                                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                        val temp = NoteState(note)
                                        temp.title = title
                                        updateNote(temp)
                                        focusManager.clearFocus()
                                        return@onKeyEvent true
                                    }
                                    false
                                }
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            textStyle = TextStyle(
                                fontSize = 20.sp,
                            ),
                            singleLine = true,
                        )
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            { deleteNote(note) },
                            modifier = Modifier.size(40.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Remove Note",
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = expanded || alwaysExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        var description by remember {
                            mutableStateOf(note.description)
                        }
                        Box {
                            BasicTextField(
                                value = description ?: "",
                                onValueChange = {
                                    description = it
                                },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                ),
                            )
                            if (description.isNullOrEmpty()) {
                                Text(
                                    text = "Description...",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        if (description != note.description) {
                            Button(
                                onClick = {
                                    val temp = NoteState(note)
                                    temp.description = description
                                    updateNote(temp)
                                },
                                modifier = Modifier.height(32.dp),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Text(text = "Save")
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = {
                            showSetActivationDateDialog = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                    ) {
                        Text(
                            text = "Activation Date: ${
                                note.activationDateTime?.format(
                                    dateTimeFormatter
                                ) ?: "-"
                            }",
                            fontSize = 16.sp,
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    ReminderCards(
                        note.id!!,
                        note.reminders,
                        insertReminder,
                        updateReminder,
                        deleteReminder,
                    )
                }
            }
        }
    }

    if (showProjectSelector) {
        ProjectSelector(
            projects = projects,
            updateNotesProject = {
                val temp = NoteState(note)
                temp.project = it
                updateNote(temp)
            }
        ) {
            showProjectSelector = false
        }
    }

    if (showEditDeadlineDialog) {
        DateTimePickerDialog(setDateTime = {
            val temp = NoteState(note)
            temp.deadline = it
            updateNote(temp)
        }, allowNone = true) {
            showEditDeadlineDialog = false
        }
    }

    if (showSetActivationDateDialog) {
        DateTimePickerDialog(setDateTime = {
            val temp = NoteState(note)
            temp.activationDateTime = it
            updateNote(temp)
        }, allowNone = true) {
            showSetActivationDateDialog = false
        }
    }
}
