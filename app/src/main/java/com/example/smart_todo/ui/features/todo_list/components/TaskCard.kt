package com.example.smart_todo.ui.features.todo_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_todo.domain.model.ToDoTask
import com.example.smart_todo.ui.theme.SmarttodoTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.smart_todo.domain.model.Priority
import com.example.smart_todo.ui.utils.toFormattedDateTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun TaskCard(
    task: ToDoTask,
    onCheckedChange: (Boolean) -> Unit,
    onTaskClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
) {

    var isMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(4.dp))

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTaskClick(task.id) }
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Время
                    SuggestionChip(
                        onClick = { },
                        label = {
                            Text(
                                text = task.deadLine.toFormattedDateTime(),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                        ),
                        border = null
                    )

                    // Приоритет
                    SuggestionChip(
                        onClick = { },
                        label = {
                            Text(
                                text = task.priority.toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(task.priority?.color ?: 0xFF00C980)
                        ),
                        border = null
                    )
                }
            }
            Box {
                IconButton(onClick = { isMenuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Опции",
                        tint = Color(0xFF49454F)
                    )
                }

                // Само выпадающее меню
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    modifier = Modifier.background(Color.White) // Белый фон как на макете
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Редактировать",
                                color = Color(0xFF1D1B20),
                                fontSize = 16.sp
                            )
                        },
                        onClick = {
                            isMenuExpanded = false
                            onEditClick(task.id) // Передаем ID наружу
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Удалить",
                                color = Color(0xFFB3261E), // Красный цвет текста для удаления
                                fontSize = 16.sp
                            )
                        },
                        onClick = {
                            isMenuExpanded = false
                            onDeleteClick(task.id) // Передаем ID наружу
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    SmarttodoTheme {
        TaskCard(
            task = ToDoTask(
            title = "Test task",
            description = "Test description",
            deadLine = 1320,
            isCompleted = true,
            category = null,
            priority = Priority.HIGH,
            reminderTime = null,
            createdAt = 0
            ),
            onCheckedChange = {},
            onTaskClick = {}
        )
    }
}

