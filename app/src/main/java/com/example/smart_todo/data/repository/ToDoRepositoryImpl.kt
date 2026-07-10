package com.example.smart_todo.data.repository

import com.example.smart_todo.data.local.ToDoDao
import com.example.smart_todo.data.mapper.toDomain
import com.example.smart_todo.data.mapper.toEntity
import com.example.smart_todo.domain.model.ToDoTask
import com.example.smart_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToDoRepositoryImpl(private val dao: ToDoDao): ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> {
        return dao.getAllTasks().map { todoList -> todoList.map { it.toDomain() }}
    }

    override fun getTaskById(id: Int): Flow<ToDoTask?> {
        return dao.getTaskById(id).map {it.toDomain()}
    }

    override suspend fun insertTask(task: ToDoTask) {
        dao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: ToDoTask) {
        dao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: ToDoTask) {
        dao.deleteTask(task.toEntity())
    }
}