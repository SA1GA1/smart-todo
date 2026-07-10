package com.example.smart_todo.domain.repository

import com.example.smart_todo.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllTasks(): Flow<List<ToDoTask>>

    fun getTaskById(id: Int): Flow<ToDoTask?>

    suspend fun insertTask(task: ToDoTask)

    suspend fun updateTask(task: ToDoTask)

    suspend fun deleteTask(task: ToDoTask)
}