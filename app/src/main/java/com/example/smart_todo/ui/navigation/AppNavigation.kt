package com.example.smart_todo.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
interface AppNavigation {
    @Serializable data object Onboarding: AppNavigation
    @Serializable data object ToDoList: AppNavigation
    @Serializable data class TaskDetails(val id: Int? = null): AppNavigation
    @Serializable data object Statistics: AppNavigation
}