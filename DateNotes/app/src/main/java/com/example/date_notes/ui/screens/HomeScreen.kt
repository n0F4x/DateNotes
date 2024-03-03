package com.example.date_notes.ui.screens

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.ui.compoments.AddNoteDialog
import com.example.date_notes.ui.compoments.MoreMenu
import com.example.date_notes.ui.compoments.ProjectPicker
import com.example.date_notes.ui.compoments.ProjectsDialog
import com.example.date_notes.ui.screens.views.CalendarView
import com.example.date_notes.ui.screens.views.ListView
import com.example.date_notes.ui.view_models.HomeViewModel
import java.time.LocalDateTime
import java.util.Locale.filter

object Routes {
    const val listView = "list_view"
    const val calendarView = "calendar_view"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val notes by homeViewModel.notes.observeAsState(initial = emptyList())
    val projects by homeViewModel.projects.observeAsState(initial = emptyList())

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var selectedProject by remember {
        mutableStateOf<Project?>(null)
    }
    var showCompleted by remember {
        mutableStateOf(false)
    }
    var showInactive by remember {
        mutableStateOf(false)
    }

    val notesFilter by remember {
        mutableStateOf(filter@{ note: Note ->
            if (selectedProject != null && note.project != selectedProject) {
                return@filter false
            }
            if (!showCompleted && note.state == Note.State.Done) {
                return@filter false
            }
            val activationDateTime = note.activationDateTime
            activationDateTime?.let {
                if (!showInactive && activationDateTime.isAfter(LocalDateTime.now())) {
                    return@filter false
                }
            }
            true
        })
    }

    var showAddNoteDialog by remember {
        mutableStateOf(false)
    }
    var showProjectsDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("DateNotes")
                },
                actions = {
                    ProjectPicker(selectedProject, projects) {
                        selectedProject = it
                    }

                    navBackStackEntry?.destination?.route?.let { NavIcon(it, navController) }

                    MoreMenu(
                        showCompleted = showCompleted,
                        onToggleCompleted = {
                            showCompleted = !showCompleted
                        },
                        showInactive = showInactive,
                        onToggleInactive = {
                            showInactive = !showInactive
                        },
                        showProjectsDialog = { showProjectsDialog = true },
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddNoteDialog = true },
            ) {
                Icon(Icons.Filled.Add, "Add TODO")
            }
        },
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "list_view") {
            composable(Routes.listView) {
                ListView(
                    innerPadding = innerPadding,
                    notes = notes.filter(notesFilter),
                    updateNote = {
                        homeViewModel.updateNote(it)
                    },
                    deleteNote = {
                        homeViewModel.deleteNote(it)
                    },
                    projects = projects,
                    insertReminder = {
                        homeViewModel.insertReminder(it)
                    },
                    updateReminder = {
                        homeViewModel.updateReminder(it)
                    },
                    deleteReminder = {
                        homeViewModel.deleteReminder(it)
                    },
                )
            }
            composable(Routes.calendarView) {
                CalendarView(
                    innerPadding = innerPadding,
                    notes = notes.filter(notesFilter),
                    updateNote = {
                        homeViewModel.updateNote(it)
                    },
                    deleteNote = {
                        homeViewModel.deleteNote(it)
                    },
                    projects = projects,
                    insertReminder = {
                        homeViewModel.insertReminder(it)
                    },
                    updateReminder = {
                        homeViewModel.updateReminder(it)
                    },
                    deleteReminder = {
                        homeViewModel.deleteReminder(it)
                    },
                )
            }
        }
    }

    if (showAddNoteDialog) {
        AddNoteDialog(addNote = { homeViewModel.insertNote(it) }) {
            showAddNoteDialog = false
        }
    }

    if (showProjectsDialog) {
        ProjectsDialog(
            addProject = { homeViewModel.insertProject(it) },
            deleteProject = { homeViewModel.deleteProject(it) },
            deleteProjectAndNotes = { homeViewModel.deleteProject(it, true) },
            updateProject = {
                homeViewModel.updateProject(
                    it
                )
            },
            projects = projects,
        ) {
            showProjectsDialog = false
        }
    }
}

@Composable
fun ListIcon(navController: NavHostController) {
    IconButton(
        onClick = {
            navController.navigate(Routes.listView)
        },
    ) {
        Icon(
            Icons.Filled.List,
            contentDescription = "List",
        )
    }
}

@Composable
fun CalendarIcon(navController: NavHostController) {
    IconButton(
        onClick = { navController.navigate(Routes.calendarView) },
    ) {
        Icon(
            Icons.Filled.DateRange,
            contentDescription = "Calendar",
        )
    }
}

@Composable
fun NavIcon(currentRoute: String, navController: NavHostController) {
    when (currentRoute) {
        Routes.listView -> CalendarIcon(navController)
        Routes.calendarView -> ListIcon(navController)
    }
}
