package com.example.smart_todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo_table WHERE id = :id")
    fun getTaskById(id: Int): Flow<ToDoEntity?>

    @Insert
    suspend fun insertTask(task: ToDoEntity)

    @Update
    suspend fun updateTask(task: ToDoEntity)

    @Delete
    suspend fun deleteTask(task: ToDoEntity)
}