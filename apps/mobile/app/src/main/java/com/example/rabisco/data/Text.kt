package com.example.rabisco.data

import java.util.Date

data class Text(
    val id: Long,
    val title: String,
    val content: String,
    val date: Date,
    val wordCount: Int,
    val category: String
)