package com.example.smart_todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smart_todo.domain.model.Priority

@Entity(tableName= "todo_table")
data class ToDoEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: Priority?,
    val createdAt: Long,
    val deadLine: Long?,
    val isCompleted: Boolean = false,
    val category: String?,
    val reminderTime: Long?
)