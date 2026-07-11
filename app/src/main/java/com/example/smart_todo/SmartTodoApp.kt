package com.example.smart_todo

import android.app.Application
import com.example.smart_todo.di.databaseModule
import com.example.smart_todo.di.repositoryModule
import com.example.smart_todo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SmartTodoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SmartTodoApp)
            modules(databaseModule, repositoryModule, viewModelModule)
        }
    }
}