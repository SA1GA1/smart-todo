package com.example.smart_todo.ui.features.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_todo.domain.model.Priority
import com.example.smart_todo.domain.model.ToDoTask
import com.example.smart_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TaskDetailsViewModel (private val repository: ToDoRepository): ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailsUiState())
    val uiStateFlow = _uiState.asStateFlow()

    fun loadTask(id: Int?) {
        if (id == null) {
            _uiState.update { TaskDetailsUiState() }
            return
        }

        viewModelScope.launch {
            repository.getTaskById(id).firstOrNull()?.let { task ->
                val (dateStr, timeStr) = formatDeadline(task.deadLine)
                _uiState.update { currentState ->
                    currentState.copy(
                        title = task.title,
                        description = task.description ?: "",
                        priority = task.priority ?: Priority.LOW,
                        deadline = task.deadLine,
                        formattedDate = dateStr,
                        formattedTime = timeStr,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update {it.copy(title = newTitle)}
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update {it.copy(description = newDescription)}
    }

    fun onPriorityChange(newPriority: Priority) {
        _uiState.update {it.copy(priority = newPriority)}
    }

    fun saveTask(id: Int?, onSuccess: () -> Unit) {
        val state = _uiState.value

        if (state.title.isBlank()) return

        viewModelScope.launch {
            val task = ToDoTask(
                id = id ?: 0,
                title = state.title,
                description = state.description,
                priority = state.priority,
                deadLine = state.deadline,
                isCompleted = false,
                category = null,
                reminderTime = null,
                createdAt = System.currentTimeMillis()
            )

            if (id == null) {
                repository.insertTask(task)
            }
            else {
                repository.updateTask(task)
            }

            onSuccess()

        }
    }

    fun onDateChange(selectedMillis: Long) {
        val selectedDate = Instant.ofEpochMilli(selectedMillis)
            .atZone(ZoneId.of("UTC"))
            .toLocalDate()

        val currentDeadline = _uiState.value.deadline ?: System.currentTimeMillis()
        val currentTime = Instant.ofEpochMilli(currentDeadline)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()

        val newDeadline = selectedDate
            .atTime(currentTime)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        updateDeadline(newDeadline)
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val currentDeadline = _uiState.value.deadline ?: System.currentTimeMillis()

        val currentDate = Instant.ofEpochMilli(currentDeadline)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val newDeadline = LocalTime.of(hour, minute)
            .atDate(currentDate)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        updateDeadline(newDeadline)
    }

    private fun updateDeadline(newDeadline: Long) {
        val (dateStr, timeStr) = formatDeadline(newDeadline)
        _uiState.update { it.copy(
            deadline = newDeadline,
            formattedDate = dateStr,
            formattedTime = timeStr
        )
        }
    }

    private fun formatDeadline(deadline: Long?): Pair<String, String> {
        if (deadline == null) return "Выбери дату" to "Выбери время"

        val localDateTime = Instant.ofEpochMilli(deadline)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

        return localDateTime.format(dateFormatter).replaceFirstChar { it.uppercase() } to localDateTime.format(timeFormatter)
    }
}