package com.example.smart_todo.ui.features.todo_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smart_todo.ui.theme.SmarttodoTheme

@Composable
fun ToDoListScreen() {
    Column() {
        Column() {
            Text(text = "Добрый день")
            Text(text = "Дата")
        }
        Row() {

        }
        LazyColumn() {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListScreenPreview() {
    SmarttodoTheme {
        ToDoListScreen()
    }
}