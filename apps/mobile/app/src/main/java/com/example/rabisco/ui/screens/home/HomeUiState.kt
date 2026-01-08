package com.example.rabisco.ui.screens.home

data class HomeUiState (
    val streak: Int = 0,
    val totalXp: Int = 0,
    val totalTexts: Int = 0,
    val textsWeek: Int = 0,
    val promptOfDay: String = "algum promtp genérico para fazer o teste... depois eu mudo",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success:Boolean = false,
)