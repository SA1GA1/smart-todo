package com.example.smart_todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    // получение всех задач
    @Query("SELECT * FROM todo_table ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<ToDoEntity>>

    // получение конкретной задачи по id
    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getTaskById(id: Int): Flow<ToDoEntity>

    // добавление задачи
    @Insert suspend fun insertTask(task: ToDoEntity)

    // обновление задачи
    @Update
    suspend fun updateTask(task: ToDoEntity)

    // удаление задачи
    @Delete
    suspend fun deleteTask(task: ToDoEntity)
}