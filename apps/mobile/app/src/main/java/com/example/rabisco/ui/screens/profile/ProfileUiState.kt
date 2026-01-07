package com.example.rabisco.ui.screens.profile

data class ProfileUiState(
    val userName: String = "Usuário",
    val userEmail: String = "usuario@email.com",
    val isDarkMode: Boolean = false,
    val isNotificationsEnabled: Boolean = true,
    val notificationTime: String = "20:00",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showLogoutDialog: Boolean = false,
    val isLoggedOut: Boolean = false
)