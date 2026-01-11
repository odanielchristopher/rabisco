package com.example.rabisco.ui.screens.auth

import com.example.rabisco.ui.components.ToastData

data class AuthUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val accessToken: String? = null,
    val toast: ToastData? = null
)
