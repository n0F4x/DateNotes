package com.example.date_notes.ui.compoments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.date_notes.model.Project

@Composable
fun ProjectSelector(
    projects: List<Project>,
    updateNotesProject: (Project?) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        val currentProjects = remember {
            mutableStateListOf<Project>()
        }
        currentProjects.addAll(projects)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Select project", fontSize = 26.sp)
                    Spacer(Modifier.height(12.dp))

                    OutlinedCard(Modifier.padding(horizontal = 8.dp)) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(currentProjects, { it.id!! }) { project ->
                                TextButton(
                                    onClick = {
                                        updateNotesProject(project)
                                        onDismissRequest()
                                    },
                                ) {
                                    Text(text = project.name)
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    TextButton(
                        onClick = {
                            updateNotesProject(null)
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("No project")
                    }
                    TextButton(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
