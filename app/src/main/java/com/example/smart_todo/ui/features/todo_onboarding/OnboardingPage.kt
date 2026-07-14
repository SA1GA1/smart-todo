package com.example.smart_todo.ui.features.todo_onboarding

sealed class OnboardingPage (
    val title: String,
    val description: String
) {
    data object First: OnboardingPage (
        title = "Управляй задачами",
        description = "приложение помогает организовать день и ничего не забыть"
    )

    data object Second: OnboardingPage(
        title = "Анализируй свой прогресс",
        description = "следи за своей продуктивностью прямо в приложении",
    )

    data object Third: OnboardingPage(
        title = "AI помощник",
        description = "Помощь в оптимизации создания задач"
    )
}