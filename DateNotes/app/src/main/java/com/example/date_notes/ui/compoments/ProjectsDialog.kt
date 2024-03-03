package com.example.date_notes.ui.compoments

import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.date_notes.model.Project
import com.example.date_notes.ui.state.ProjectState

@Composable
fun ProjectsDialog(
    addProject: (Project) -> Unit,
    deleteProject: (Project) -> Unit,
    deleteProjectAndNotes: (Project) -> Unit,
    updateProject: (Project) -> Unit,
    projects: List<Project>,
    onDismissRequest: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var projectToDelete by remember {
        mutableStateOf<Project?>(null)
    }

    Dialog(onDismissRequest = onDismissRequest) {
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
                    Text(text = "Manage projects", fontSize = 26.sp)
                    Spacer(Modifier.height(12.dp))

                    var newProjectName by remember { mutableStateOf("") }
                    val invalidProjectName = "no project"
                    var borderColor by remember { mutableStateOf(Color.Blue) }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Add new project ", textAlign = TextAlign.Center)
                        BasicTextField(
                            value = newProjectName,
                            onValueChange = {
                                if (it.length <= 11) newProjectName = it
                                borderColor = if (newProjectName.lowercase() == invalidProjectName || projects
                                        .map(
                                            Project::name
                                        )
                                        .contains(newProjectName)
                                ) {
                                    Color.Red
                                } else {
                                    Color.Blue
                                }
                            },
                            modifier = Modifier
                                .onKeyEvent {
                                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                        if (newProjectName.lowercase() == invalidProjectName ||
                                            newProjectName.isBlank() ||
                                            projects
                                                .map(
                                                    Project::name
                                                )
                                                .contains(newProjectName)
                                        ) {
                                            return@onKeyEvent false
                                        }
                                        addProject(ProjectState(name = newProjectName))
                                        newProjectName = ""
                                        focusManager.clearFocus()
                                        return@onKeyEvent true
                                    }
                                    false
                                }
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            singleLine = true,
                        ) { innerTextField ->
                            OutlinedCard(
                                modifier = Modifier.width(92.dp),
                                shape = RoundedCornerShape(4.dp),
                                border = BorderStroke(1.dp, borderColor),
                            ) {
                                Row(Modifier.padding(6.dp)) {
                                    innerTextField()
                                }
                            }
                        }
                        Text(text = ".", textAlign = TextAlign.Center)
                    }
                    Spacer(Modifier.height(12.dp))
                    OutlinedCard(Modifier.padding(horizontal = 8.dp)) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(projects, { it.id!! }) { project ->
                                var projectName by remember {
                                    mutableStateOf(project.name)
                                }
                                val dangerTextStyle = TextStyle(color = Color.Red)
                                val normalTextStyle = TextStyle(color = Color.Black)
                                var textStyle by remember { mutableStateOf(normalTextStyle) }

                                Row(
                                    modifier = Modifier
                                        .width(180.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Spacer(Modifier.width(6.dp))
                                    BasicTextField(
                                        value = projectName,
                                        onValueChange = {
                                            if (it.length <= 11) projectName = it
                                            textStyle =
                                                if (
                                                    projectName.lowercase() == invalidProjectName ||
                                                    projectName.isBlank() ||
                                                    projectName != project.name && projects
                                                        .map(
                                                            Project::name
                                                        )
                                                        .contains(projectName)
                                                ) {
                                                    dangerTextStyle
                                                } else {
                                                    normalTextStyle
                                                }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .onKeyEvent {
                                                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                                    if (projectName.lowercase() == invalidProjectName ||
                                                        projectName.isBlank() ||
                                                        projectName != project.name && projects
                                                            .map(
                                                                Project::name
                                                            )
                                                            .contains(projectName)
                                                    ) {
                                                        return@onKeyEvent false
                                                    }
                                                    project.name = projectName
                                                    updateProject(project)
                                                    focusManager.clearFocus()
                                                    return@onKeyEvent true
                                                }
                                                false
                                            }
                                            .focusRequester(focusRequester),
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                        textStyle = textStyle,
                                        singleLine = true,
                                    )
                                    IconButton(
                                        onClick = {
                                            projectToDelete = project
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Remove project",
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                ) {
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
    if (projectToDelete != null) {
        Dialog(onDismissRequest = { projectToDelete = null }) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Remove related notes as well?"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { projectToDelete = null },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Dismiss")
                        }
                        TextButton(
                            onClick = {
                                projectToDelete?.let {
                                    deleteProject(it)
                                }
                                projectToDelete = null
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("No")
                        }
                        TextButton(
                            onClick = {
                                projectToDelete?.let { deleteProjectAndNotes(it) }
                                projectToDelete = null
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Yes")
                        }
                    }
                }
            }
        }
    }
}
