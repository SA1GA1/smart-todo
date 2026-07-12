package com.example.smart_todo.ui.features.task_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smart_todo.domain.model.Priority
import com.example.smart_todo.ui.features.task_details.components.DateTimePickerButton
import com.example.smart_todo.ui.features.task_details.components.MyDatePickerDialog
import com.example.smart_todo.ui.features.task_details.components.MyTimePickerDialog
import com.example.smart_todo.ui.theme.SmarttodoTheme
import com.example.smart_todo.ui.theme.notSelectHighPriorityColor
import com.example.smart_todo.ui.theme.notSelectLowPriorityColor
import com.example.smart_todo.ui.theme.notSelectMediumPriorityColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskDetailsScreen(
    id: Int? = null,
    onBackClick: () -> Unit,
    viewModel: TaskDetailsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        viewModel.loadTask(id)
    }

    TaskDetailsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onSaveClick = {
            viewModel.saveTask(id = id, onSuccess = onBackClick)
        },
        onDateClick = { showDatePicker = true },
        onTimeClick = { showTimePicker = true },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onPriorityChange = viewModel::onPriorityChange
    )

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSealed = { millis ->
                viewModel.onDateChange(millis)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        MyTimePickerDialog(
            onTimeSelected = { hour, minute ->
                viewModel.onTimeChange(hour, minute)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsContent(
    uiState: TaskDetailsUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {onSaveClick()},
                        enabled = uiState.title.isNotBlank()
                    ) {
                        Text(
                            text = "Сохранить",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (uiState.title.isNotBlank()) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                                }
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Название задачи
                TextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    placeholder = {
                        Text(
                            text = "Название задачи",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Описание задачи
                TextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    placeholder = { Text("Описание, детали ...") },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                // Выбор Даты и Времени
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Кнопка даты
                    DateTimePickerButton(
                        text = uiState.formattedDate,
                        icon = Icons.Default.DateRange,
                        onClick = onDateClick,
                        modifier = Modifier.weight(1f)
                    )
                    // Кнопка времени
                    DateTimePickerButton(
                        text = uiState.formattedTime,
                        icon = Icons.Default.AccessTime,
                        onClick = onTimeClick,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Выбор приоритета
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Приоритет",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Priority.entries.forEachIndexed { index, priorityOption ->
                            val isSelected = uiState.priority == priorityOption

                            val containerColor = if (isSelected) {
                                when (priorityOption) {
                                    Priority.LOW -> notSelectLowPriorityColor
                                    Priority.MEDIUM -> notSelectMediumPriorityColor
                                    Priority.HIGH -> notSelectHighPriorityColor
                                }
                            } else {
                                Color.Transparent
                            }

                            val contentColor = if (isSelected) {
                                when (priorityOption) {
                                    Priority.LOW -> Priority.LOW.color
                                    Priority.MEDIUM -> Priority.MEDIUM.color
                                    Priority.HIGH -> Priority.HIGH.color
                                }
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }

                            SegmentedButton(
                                selected = isSelected,
                                onClick = { onPriorityChange(priorityOption) },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = Priority.entries.size
                                ),
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = containerColor,
                                    activeContentColor = contentColor as Color,
                                    inactiveContainerColor = Color.Transparent,
                                    inactiveContentColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Text(
                                    text = when (priorityOption) {
                                        Priority.LOW -> "Низкий"
                                        Priority.MEDIUM -> "Средний"
                                        Priority.HIGH -> "Высокий"
                                    },
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsScreenPreview() {
    SmarttodoTheme {
        TaskDetailsContent(
            uiState = TaskDetailsUiState(
                title = "Название задачи",
                description = "Описание задачи",
                priority = Priority.MEDIUM
            ),
            onBackClick = {},
            onTitleChange = {},
            onDescriptionChange = {},
            onPriorityChange = {},
            onSaveClick = {},
            onDateClick = {},
            onTimeClick = {}
        )
    }
}