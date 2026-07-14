package com.example.smart_todo.ui.features.todo_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smart_todo.ui.theme.SmarttodoTheme
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smart_todo.ui.features.todo_list.components.TaskCard
import com.example.smart_todo.ui.theme.DarkPurple
import com.example.smart_todo.ui.theme.LightPurple
import com.example.smart_todo.ui.theme.Purple40
import com.example.smart_todo.ui.theme.PurpleGrey40
import com.example.smart_todo.domain.model.Priority
import com.example.smart_todo.domain.model.ToDoTask

@Composable
fun ToDoListScreen(
    viewModel: ToDoListViewModel = koinViewModel(),
    onTaskClick: (Int) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    ToDoListContent(
        uiState = uiState,
        onTaskClick = onTaskClick,
        onToggleTask = { viewModel.toggleTask(it) }
    )
}

@Composable
fun ToDoListContent(
    uiState: ToDoListUiState,
    onTaskClick: (Int) -> Unit,
    onToggleTask: (ToDoTask) -> Unit,
    modifier: Modifier = Modifier
) {
    // Экран загрузки
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        // Основной контент
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Шапка приветствия
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Добрый день",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = uiState.currentStringDate,
                            style = MaterialTheme.typography.bodyMedium.copy(color = PurpleGrey40)
                        )
                    }
                }
            }

            // Виджет прогресса
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = LightPurple),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Сегодня выполнено ${uiState.completedCount} из ${uiState.totalCount}", fontWeight = FontWeight.SemiBold, color = DarkPurple)
                            Text("${uiState.progressPercentage}%", fontWeight = FontWeight.SemiBold, color = DarkPurple)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { uiState.progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = Purple40,
                            trackColor = LightPurple.copy(alpha = 0.5f)
                        )
                    }
                }
            }
            // Список сегодняшних задач
            items(uiState.todayTasks) { task ->
                TaskCard(
                    task = task,
                    onCheckedChange = { onToggleTask(task) },
                    onTaskClick = onTaskClick
                )
            }

            // Список предстоящих задач
            items(uiState.upcomingTasks) { task ->
                TaskCard(
                    task = task,
                    onCheckedChange = { onToggleTask(task) },
                    onTaskClick = onTaskClick
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ToDoListContentPreview() {
    val sampleTasks = listOf(
        ToDoTask(
            id = 1,
            title = "Сделать зарядку",
            description = "15 минут утренней гимнастики",
            priority = Priority.HIGH,
            createdAt = System.currentTimeMillis(),
            deadLine = System.currentTimeMillis(),
            isCompleted = false,
            category = "Здоровье",
            reminderTime = null
        ),
        ToDoTask(
            id = 2,
            title = "Купить продукты",
            description = "Хлеб, молоко, яйца",
            priority = Priority.MEDIUM,
            createdAt = System.currentTimeMillis(),
            deadLine = System.currentTimeMillis(),
            isCompleted = true,
            category = "Покупки",
            reminderTime = null
        )
    )

    val sampleUiState = ToDoListUiState(
        tasks = sampleTasks,
        isLoading = false,
        completedCount = 1,
        totalCount = 2,
        progressFraction = 0.5f,
        progressPercentage = 50,
        todayTasks = sampleTasks.take(1),
        upcomingTasks = sampleTasks.drop(1),
        currentStringDate = "Понедельник, 1 января"
    )

    SmarttodoTheme {
        ToDoListContent(
            uiState = sampleUiState,
            onTaskClick = {},
            onToggleTask = {}
        )
    }
}