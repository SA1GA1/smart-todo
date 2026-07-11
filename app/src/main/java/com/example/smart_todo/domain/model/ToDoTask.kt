package com.example.smart_todo.domain.model

enum class Priority(val color: Long) {
    LOW(0xFF00C980),
    MEDIUM(0xFFFFCA28),
    HIGH(0xFFE53935)
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
