package com.example.date_notes.ui.compoments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MoreMenu(
    showCompleted: Boolean,
    onToggleCompleted: () -> Unit,
    showInactive: Boolean,
    onToggleInactive: () -> Unit,
    showProjectsDialog: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "View options",
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                text = {
                    if (showCompleted)
                        Text("Hide completed")
                    else {
                        Text("Show completed")
                    }
                },
                onClick = onToggleCompleted
            )
            DropdownMenuItem(
                text = {
                    if (showInactive)
                        Text("Hide inactive")
                    else {
                        Text("Show inactive")
                    }
                },
                onClick = onToggleInactive
            )
            DropdownMenuItem(
                text = { Text("Manage projects") },
                onClick = {
                    showProjectsDialog()
                    expanded = false
                }
            )
        }
    }
}
