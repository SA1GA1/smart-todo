package com.example.smart_todo.ui.features.todo_stats

import com.example.smart_todo.domain.model.Priority

data class StatisticsUiState (
    val completedCount: Int = 0,
    val totalCount: Int = 0,
    val completionRate: Float = 0f,
    val tasksByPriority: Map<Priority, Int> = emptyMap(),
    val isLoading: Boolean
)