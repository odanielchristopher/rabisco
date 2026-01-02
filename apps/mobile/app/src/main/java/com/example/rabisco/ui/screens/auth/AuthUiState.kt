package com.example.rabisco.ui.screens.auth

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
