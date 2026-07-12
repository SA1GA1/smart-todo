package com.example.smart_todo.ui.features.task_details

import com.example.smart_todo.domain.model.Priority

data class TaskDetailsUiState (
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val deadline: Long? = null,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val formattedDate: String = "",
    val formattedTime: String = ""
)