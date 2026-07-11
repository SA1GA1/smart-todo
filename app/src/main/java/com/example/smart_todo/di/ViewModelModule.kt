package com.example.smart_todo.di

import com.example.smart_todo.ui.features.todo_list.ToDoListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ToDoListViewModel(get()) }
}