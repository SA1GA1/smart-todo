package com.example.smart_todo.ui.features.todo_stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_todo.domain.model.Priority
import com.example.smart_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class StatisticsViewModel (private val repository: ToDoRepository): ViewModel() {
    val uiState: StateFlow<StatisticsUiState> = repository.getAllTasks().map { tasks ->
        val completed = tasks.count {it.isCompleted}
        val total = tasks.size

        StatisticsUiState (
            completedCount = completed,
            totalCount = total,
            completionRate = if (total > 0) completed.toFloat() / total else 0f,
            tasksByPriority = tasks.filter { it.isCompleted }.groupBy { it.priority ?: Priority.LOW }.mapValues { it.value.size },
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatisticsUiState(isLoading = true)
    )
}