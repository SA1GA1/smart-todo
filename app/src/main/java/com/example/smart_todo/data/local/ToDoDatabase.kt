package com.example.smart_todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smart_todo.data.mapper.Converter

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase()  {
    abstract fun getToDoDao(): ToDoDao
}