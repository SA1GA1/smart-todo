package com.example.smart_todo.ui.features.todo_list

import com.example.smart_todo.domain.model.ToDoTask

data class ToDoListUiState (
    val tasks: List<ToDoTask> = emptyList(),
    val isLoading: Boolean = true,
    val completedCount: Int = 0,
    val totalCount: Int = 0,
    val progressFraction: Float = 0f,
    val progressPercentage: Int = 0,
    val todayTasks: List<ToDoTask> = emptyList(),
    val upcomingTasks: List<ToDoTask> = emptyList(),
    val currentStringDate: String = ""
)