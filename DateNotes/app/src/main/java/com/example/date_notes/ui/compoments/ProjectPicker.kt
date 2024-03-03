package com.example.date_notes.ui.compoments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.date_notes.model.Project

@Composable
fun ProjectPicker(selectedProject: Project?, projects: List<Project>, pickProject: (Project?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    TextButton(onClick = { expanded = !expanded }, modifier = Modifier.width(92.dp)) {
        Text(
            text = selectedProject?.name ?: "All",
            textAlign = TextAlign.Center
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.height(IntrinsicSize.Min),
    ) {
        Column(
            modifier = Modifier
                .width(92.dp)
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = {
                    expanded = false
                    pickProject(null)
                },
            ) {
                Text(text = "All")
            }
            for (project in projects) {
                TextButton(
                    onClick = {
                        expanded = false
                        pickProject(project)
                    },
                ) {
                    Text(text = project.name)
                }
            }
        }
    }
}
