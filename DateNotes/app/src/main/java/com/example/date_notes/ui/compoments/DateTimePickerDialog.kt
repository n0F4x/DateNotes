package com.example.date_notes.ui.compoments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vsnappy1.datepicker.DatePicker
import com.vsnappy1.datepicker.ui.model.DatePickerConfiguration
import com.vsnappy1.timepicker.TimePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun DateTimePickerDialog(
    setDateTime: (LocalDateTime?) -> Unit,
    allowNone: Boolean = false,
    onDismissRequest: () -> Unit
) {
    var date by remember {
        mutableStateOf<LocalDate>(LocalDate.now())
    }
    var time by remember {
        mutableStateOf<LocalTime>(LocalTime.now())
    }

    Dialog(onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.padding(12.dp).fillMaxWidth()) {
                DatePicker(onDateSelected = { year, month, day ->
                    date = LocalDate.of(year, month + 1, day)
                })
                TimePicker(onTimeSelected = { hour, minute ->
                    time = LocalTime.of(hour, minute)
                })
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Close")
                    }
                    TextButton(
                        onClick = {
                            setDateTime(null)
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("None")
                    }
                    TextButton(
                        onClick = {
                            setDateTime(LocalDateTime.of(date, time))
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}
