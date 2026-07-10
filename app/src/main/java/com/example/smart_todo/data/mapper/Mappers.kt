package com.example.smart_todo.data.mapper

import com.example.smart_todo.data.local.ToDoEntity
import com.example.smart_todo.domain.model.ToDoTask

fun ToDoEntity.toDomain(): ToDoTask {
    return ToDoTask(
        id = id,
        title = title,
        description = description,
        priority = priority,
        createdAt = createdAt,
        deadLine = deadLine,
        isCompleted = isCompleted,
        category = category,
        reminderTime = reminderTime
    )
}

fun ToDoTask.toEntity(): ToDoEntity {
    return ToDoEntity (
        id = id,
        title = title,
        description = description,
        priority = priority,
        createdAt = createdAt,
        deadLine = deadLine,
        isCompleted = isCompleted,
        category = category,
        reminderTime = reminderTime
    )
}