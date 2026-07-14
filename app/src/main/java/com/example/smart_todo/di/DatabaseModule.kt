package com.example.smart_todo.di


import androidx.room.Room
import com.example.smart_todo.data.local.SettingsManager
import com.example.smart_todo.data.local.ToDoDatabase
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext


val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            "todo_database"
        ).build()
    }

    single {
        val database = get<ToDoDatabase>()
        database.getToDoDao()
    }
}

val settingsModule = module {
    single { SettingsManager(get()) }
}