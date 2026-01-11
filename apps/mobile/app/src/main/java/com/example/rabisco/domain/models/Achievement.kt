package com.example.rabisco.domain.models

data class Achievement(
    val id: String,
    val code: String,
    val title: String,
    val description: String,
    val goal: Int,
    val progress: Int,
    val achieved: Boolean,
)