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

class TaskDetailsViewModel (private val repository: ToDoRepository): ViewModel() {

    private val _uiState = MutableStateFlow(TaskDetailsUiState())
    val uiStateFlow = _uiState.asStateFlow()

    fun loadTask(id: Int?) {
        if (id == null) return

        viewModelScope.launch {
            repository.getTaskById(id).firstOrNull()?.let { task ->
                _uiState.update { currentState ->
                    currentState.copy(
                        title = task.title,
                        description = task.description ?: "",
                        priority = task.priority ?: Priority.LOW,
                        deadline = task.deadLine,
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

    fun onDeadlineChange(newDeadline: Long?) {
        _uiState.update {it.copy(deadline = newDeadline)}
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
}