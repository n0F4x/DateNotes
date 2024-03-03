package com.example.date_notes.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.date_notes.model.Project

@Entity(
    "project",
    indices = [Index(value = ["name"], unique = true)]
)
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Long? = null,

    @ColumnInfo(name = "name")
    override var name: String,
) : Project {
    constructor(project: Project) : this(id = project.id, project.name)
}
