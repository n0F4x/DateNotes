package com.example.date_notes.ui.compoments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.date_notes.model.Reminder
import com.example.date_notes.ui.state.ReminderState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy.MM.dd. - HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderCards(
    noteId: Long,
    reminders: List<Reminder>,
    insertReminder: (Reminder) -> Unit,
    updateReminder: (Reminder) -> Unit,
    deleteReminder: (Reminder) -> Unit,
) {
    var showDateTimePickerDialog by remember {
        mutableStateOf(false)
    }

    val pickerInsertReminder: (LocalDateTime?) -> Unit = { dateTime: LocalDateTime? ->
        dateTime?.let { insertReminder(ReminderState(noteId = noteId, dateTime = it)) }
    }
    val pickerSetDateTimeOf: (Reminder) -> (LocalDateTime?) -> Unit = { reminder: Reminder ->
        { dateTime: LocalDateTime? ->
            dateTime?.let {
                val temp = ReminderState(reminder)
                temp.dateTime = it
                updateReminder(temp)
            }
        }
    }

    var onDateTimeSet by remember {
        mutableStateOf<(LocalDateTime?) -> Unit>({})
    }

    Card(
        onClick = {},
        modifier = Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Reminders:")
                OutlinedButton(
                    onClick = {
                        onDateTimeSet = pickerInsertReminder
                        showDateTimePickerDialog = true
                    },
                    modifier = Modifier
                        .size(32.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = null,
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add reminder",
                    )
                }
            }
            OutlinedCard {
                LazyColumn(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
                ) {
                    items(reminders, { it.id!! }) { reminder ->
                        var expanded by remember { mutableStateOf(false) }
                        ElevatedCard(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.padding(vertical = 1.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(Modifier.width(8.dp))
                                    TextButton(
                                        onClick = {
                                            onDateTimeSet = pickerSetDateTimeOf(reminder)
                                            showDateTimePickerDialog = true
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(0.dp),
                                    ) {
                                        Text(
                                            text = reminder.dateTime.format(dateTimeFormatter),
                                            fontSize = 16.sp,
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        deleteReminder(reminder)
                                    },
                                    modifier = Modifier
                                        .size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Remove reminder",
                                    )
                                }
                            }
                            AnimatedVisibility(
                                visible = expanded,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Max)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    var text by remember {
                                        mutableStateOf(reminder.text)
                                    }
                                    Row(Modifier.defaultMinSize(minHeight = 32.dp)) {
                                        Spacer(Modifier.width(8.dp))
                                        Box {
                                            BasicTextField(
                                                value = text ?: "",
                                                onValueChange = {
                                                    text = it
                                                },
                                                textStyle = TextStyle(
                                                    fontSize = 16.sp,
                                                )
                                            )
                                            if (text.isNullOrEmpty()) {
                                                Text(
                                                    text = "Custom message",
                                                    color = Color.Gray,
                                                    fontSize = 16.sp
                                                )
                                            }
                                        }
                                    }
                                    if (text != reminder.text) {
                                        Button(
                                            onClick = {
                                                val temp = ReminderState(reminder)
                                                temp.text = text
                                                updateReminder(temp)
                                            },
                                            modifier = Modifier.height(32.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            contentPadding = PaddingValues(0.dp),
                                        ) {
                                            Text(text = "Save")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDateTimePickerDialog) {
        DateTimePickerDialog(setDateTime = onDateTimeSet) {
            showDateTimePickerDialog = false
        }
    }
}
