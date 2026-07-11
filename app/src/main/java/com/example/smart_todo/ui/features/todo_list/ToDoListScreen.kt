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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smart_todo.ui.features.todo_list.components.TaskCard
import com.example.smart_todo.ui.navigation.AppNavigation

@Composable
fun ToDoListScreen(
    viewModel: ToDoListViewModel = koinViewModel(),
    onTaskClick: (Int) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    // 1. Экран загрузки
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        // 1. Основной контент
        LazyColumn(
            modifier = Modifier
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
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF49454F))
                        )
                    }
                }
            }

            // Виджет прогресса
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEADDFF)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Сегодня выполнено $uiState.completedCount из $uiState.totalCount", fontWeight = FontWeight.SemiBold, color = Color(0xFF21005D))
                            Text("$uiState.progressPercentage%", fontWeight = FontWeight.SemiBold, color = Color(0xFF21005D))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { uiState.progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = Color(0xFF6750A4),
                            trackColor = Color(0xFFF3EDF7).copy(alpha = 0.5f)
                        )
                    }
                }
            }
            // Список сегодняшних задач
            items(uiState.todayTasks) { task ->
                TaskCard(
                    task = task,
                    onCheckedChange = { viewModel.toggleTask(task) },
                    onTaskClick = onTaskClick
                )
            }

            // Список предстоящих задач
            items(uiState.upcomingTasks) { task ->
                TaskCard(
                    task = task,
                    onCheckedChange = { viewModel.toggleTask(task) },
                    onTaskClick = onTaskClick
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ToDoListScreenPreview() {
    SmarttodoTheme {
        ToDoListScreen(
            onTaskClick = {}
        )
    }
}