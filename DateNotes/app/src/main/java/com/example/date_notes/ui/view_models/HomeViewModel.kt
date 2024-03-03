package com.example.date_notes.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.date_notes.MainApplication
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import com.example.date_notes.ui.state.ReminderState
import com.example.date_notes.workers.ReminderWorker
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID
import java.util.concurrent.TimeUnit

class HomeViewModel(
    private val _application: MainApplication
) : ViewModel() {
    var notes = _application.repository.notes.asLiveData()
    val projects = _application.repository.projects.asLiveData()

    private val _workManager = WorkManager.getInstance(_application)

    fun insertNote(note: Note) = viewModelScope.launch {
        _application.repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        _application.repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        _application.repository.deleteNote(note)
    }

    fun insertProject(project: Project) = viewModelScope.launch {
        _application.repository.insertProject(project)
    }

    fun updateProject(project: Project) = viewModelScope.launch {
        _application.repository.updateProject(project)
    }

    fun deleteProject(
        project: Project,
        deleteAssociatedNotesAsWell: Boolean = false
    ) = viewModelScope.launch {
        _application.repository.deleteProject(project, deleteAssociatedNotesAsWell)
    }

    fun insertReminder(reminder: Reminder) = viewModelScope.launch {
        val temp = ReminderState(reminder)
        temp.workerId = scheduleReminder(temp)
        _application.repository.insertReminder(temp)
    }

    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        val temp = ReminderState(reminder)
        temp.workerId = scheduleReminder(temp)
        _application.repository.updateReminder(temp)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        reminder.workerId?.let { _workManager.cancelWorkById(it) }
        _application.repository.deleteReminder(reminder)
    }

    private fun scheduleReminder(
        reminder: Reminder,
    ): UUID {
        reminder.workerId?.let {
            _workManager.cancelWorkById(it)
        }

        val workRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
        workRequestBuilder.setInputData(
            workDataOf(
                ReminderWorker.textKey to reminder.text,
            )
        )
        workRequestBuilder.setInitialDelay(
            reminder.dateTime.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.now()
                .toEpochSecond(ZoneOffset.UTC),
            TimeUnit.SECONDS
        )

        val workRequest = workRequestBuilder.build()
        _workManager.enqueue(workRequest)
        return workRequest.id
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    this[APPLICATION_KEY] as MainApplication
                )
            }
        }
    }
}
