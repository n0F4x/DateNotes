package com.example.date_notes.ui.state

import com.example.date_notes.model.Project

data class ProjectState(
    override val id: Long? = null,
    override var name: String,
) : Project
