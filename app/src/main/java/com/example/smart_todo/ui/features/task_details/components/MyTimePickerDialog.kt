package com.example.smart_todo.ui.features.task_details.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@Composable
fun MyDatePickerDialog (
    onDateSealed: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePikerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePikerState.selectedDateMillis?.let { onDateSealed(it) }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
    {
        DatePicker(state = datePikerState)
    }
}