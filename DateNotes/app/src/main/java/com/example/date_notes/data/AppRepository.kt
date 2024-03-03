package com.example.date_notes.data

import androidx.annotation.WorkerThread
import com.example.date_notes.data.dao.NoteDao
import com.example.date_notes.data.dao.ProjectDao
import com.example.date_notes.data.dao.ReminderDao
import com.example.date_notes.data.entities.NoteEntity
import com.example.date_notes.data.entities.ProjectEntity
import com.example.date_notes.data.entities.ReminderEntity
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val noteDao: NoteDao,
    private val reminderDao: ReminderDao,
    private val projectDao: ProjectDao,
) {
    val notes: Flow<List<Note>> = noteDao.getAll()
    val projects: Flow<List<Project>> = projectDao.getAll()

    @WorkerThread
    suspend fun insertNote(note: Note) {
        noteDao.insert(NoteEntity(note))
    }

    @WorkerThread
    suspend fun updateNote(note: Note) {
        noteDao.update(NoteEntity(note))
    }

    @WorkerThread
    suspend fun deleteNote(note: Note) {
        noteDao.delete(NoteEntity(note))
    }

    @WorkerThread
    suspend fun insertProject(project: Project) {
        projectDao.insert(ProjectEntity(project))
    }

    @WorkerThread
    suspend fun deleteProject(
        project: Project,
        deleteAssociatedNotesAsWell: Boolean = false
    ) {
        if (deleteAssociatedNotesAsWell) {
            project.id?.let { noteDao.deleteWithProject(it) }
        }
        projectDao.delete(ProjectEntity(project))
    }

    @WorkerThread
    suspend fun updateProject(project: Project) {
        projectDao.update(ProjectEntity(project))
    }

    @WorkerThread
    suspend fun insertReminder(reminder: Reminder): Long {
        return reminderDao.insert(ReminderEntity(reminder))
    }

    @WorkerThread
    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.update(ReminderEntity(reminder))
    }

    @WorkerThread
    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(ReminderEntity(reminder))
    }
}
