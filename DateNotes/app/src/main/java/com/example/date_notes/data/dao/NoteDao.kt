package com.example.date_notes.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.date_notes.data.entities.NoteEntity
import com.example.date_notes.data.entities.NoteWithRemindersAndProject
import com.example.date_notes.model.Note
import com.example.date_notes.model.Project
import com.example.date_notes.model.Reminder
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM note ORDER BY deadline")
    fun getAll(): Flow<List<NoteWithRemindersAndProject>>

    @Insert
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query(
        "DELETE FROM note " +
                "WHERE project_id IS NOT NULL " +
                "AND project_id IN (SELECT id FROM project WHERE id = :projectId)"
    )
    suspend fun deleteWithProject(projectId: Long)
}
