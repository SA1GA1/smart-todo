package com.example.smart_todo.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.toFormattedDateTime(): String {
    if (this == null) return ""
    val date = Date(this)
    val formatter = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
    return formatter.format(date)
}