package com.example.smart_todo.di

import com.example.smart_todo.data.repository.ToDoRepositoryImpl
import com.example.smart_todo.domain.repository.ToDoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ToDoRepository> {
        ToDoRepositoryImpl(get())
    }
}