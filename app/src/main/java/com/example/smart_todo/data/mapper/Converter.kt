package com.example.smart_todo.data.mapper

import androidx.room.TypeConverter
import com.example.smart_todo.domain.model.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}