package com.example.smart_todo.ui.features.todo_list

import com.example.smart_todo.domain.model.ToDoTask

data class ToDoListUiState (
    val tasks: List<ToDoTask> = emptyList(),
    val isLoading: Boolean = true
)