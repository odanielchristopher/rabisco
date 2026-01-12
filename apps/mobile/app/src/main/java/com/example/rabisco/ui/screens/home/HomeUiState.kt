package com.example.rabisco.ui.screens.home

data class HomeUiState(
    val userName: String = "",
    val streak: Int = 0,
    val totalXp: Int = 0,
    val totalTexts: Int = 0,
    val textsWeek: Int = 0,
    val textsToday: Int = 0,
    val totalWords: Int = 0,
    val promptOfDay: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false,
)