package com.example.smart_todo.ui.features.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_todo.domain.model.ToDoTask
import com.example.smart_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class ToDoListViewModel (private val repository: ToDoRepository): ViewModel() {

    val uiStateFlow: StateFlow<ToDoListUiState> = repository.getAllTasks().map { allTasks ->
        // фильтрация по датам
        val today = LocalDate.now(ZoneId.systemDefault())

        val todayTasks = allTasks.filter { task ->
            getLocalDate(task.deadLine) == today
        }

        val upcomingTasks = allTasks.filter { task ->
            getLocalDate(task.deadLine).isAfter(today)
        }

        // получение даты в строковом представлении
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())

        val currentStringDate = today.format(formatter).replaceFirstChar { it.uppercase() }

        // расчет прогресса
        val completedCount = allTasks.count { it.isCompleted }
        val totalCount = allTasks.size
        val progressFraction = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
        val progressPercentage = (progressFraction * 100).toInt()

        // готовое ui состояние
        ToDoListUiState(
            tasks = allTasks,
            isLoading = false,
            completedCount = completedCount,
            totalCount = totalCount,
            progressFraction = progressFraction,
            progressPercentage = progressPercentage,
            currentStringDate = currentStringDate,
        )

    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ToDoListUiState(isLoading = true)
        )

    fun toggleTask(task: ToDoTask) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
    private fun getLocalDate(deadLine: Long?): LocalDate {
        return Instant.ofEpochMilli(deadLine ?: 0)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

}
