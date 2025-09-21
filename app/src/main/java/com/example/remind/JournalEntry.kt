package com.example.remind // Your package

import java.util.Date

data class JournalEntry(
    val id: Long = System.currentTimeMillis(), // Unique ID, timestamp can work for simplicity
    val content: String,
    val date: Date
)