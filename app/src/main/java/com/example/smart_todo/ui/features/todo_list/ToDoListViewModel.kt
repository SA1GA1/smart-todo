package com.example.smart_todo.ui.features.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ToDoListViewModel (repository: ToDoRepository): ViewModel() {

    val uiStateFlow: StateFlow<ToDoListUiState> = repository.getAllTasks().map { tasks ->
        ToDoListUiState(tasks = tasks, isLoading = false)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ToDoListUiState(isLoading = true)
        )
}
