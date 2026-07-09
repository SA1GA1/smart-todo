package com.example.smart_todo.domain.model

enum class Priority {
    LOW, MEDIUM, HIGH
}

data class ToDoTask(
    val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: Priority?,
    val createdAt: Long,
    val deadLine: Long?,
    val isCompleted: Boolean = false,
    val category: String?,
    val reminderTime: Long?
)
