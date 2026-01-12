package com.example.rabisco.domain.models

import java.util.Date

data class Prompt(
    val id: String,
    val prompt: String,
    val date: Date,
    val createdAt: Date,
    val updatedAt: Date,
)
