package com.example.rabisco.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.notifications.NotificationHelper
import com.example.rabisco.data.notifications.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context, private val sessionRepository: SessionRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadDarkModePreference()
        loadNotificationPreferences()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                // TODO: Puxar do back
                _uiState.update {
                    it.copy(
                        userName = "Usuário",
                        userEmail = "usuario@email.com"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Erro ao carregar dados")
                }
            }
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDarkMode = enabled) }
            try {
                sessionRepository.saveDarkMode(enabled)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Erro ao salvar: ${e.message}")
                }
            }
        }
    }

    private fun loadDarkModePreference() {
        viewModelScope.launch {
            try {
                val isDark = sessionRepository.getDarkMode()
                _uiState.update { it.copy(isDarkMode = isDark) }
            } catch (e: Exception) {
                // usar padrao (false)
            }
        }
    }

    private fun parseTime(time: String): Pair<Int, Int> {
        val parts = time.split(":")
        return Pair(parts[0].toInt(), parts[1].toInt())
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isNotificationsEnabled = enabled) }

            try {
                sessionRepository.saveNotificationsEnabled(enabled)

                if (enabled) {
                    // Verificar permissao
                    if (NotificationHelper.hasNotificationPermission(context)) {
                        // Agendar notificacao
                        val time = _uiState.value.notificationTime
                        val (hour, minute) = parseTime(time)
                        NotificationScheduler.scheduleNotification(context, hour, minute)
                    } else {
                        // Pedir permissao (vai ser tratado na tela)
                        _uiState.update { it.copy(shouldRequestPermission = true) }
                    }
                } else {
                    // Cancelar
                    NotificationScheduler.cancelNotification(context)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro: ${e.message}") }
            }
        }
    }

    fun updateNotificationTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            val timeString = String.format("%02d:%02d", hour, minute)
            _uiState.update { it.copy(notificationTime = timeString) }

            try {
                sessionRepository.saveNotificationTime(timeString)

                // Se notificacoes estao ativas, reagendar
                if (_uiState.value.isNotificationsEnabled) {
                    NotificationScheduler.scheduleNotification(context, hour, minute)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro: ${e.message}") }
            }
        }
    }

    private fun loadNotificationPreferences() {
        viewModelScope.launch {
            try {
                val isEnabled = sessionRepository.getNotificationsEnabled()
                val time = sessionRepository.getNotificationTime()
                _uiState.update {
                    it.copy(
                        isNotificationsEnabled = isEnabled,
                        notificationTime = time
                    )
                }
            } catch (e: Exception) {
                // usar padrao
            }
        }
    }

    fun resetPermissionRequest() {
        _uiState.update { it.copy(shouldRequestPermission = false) }
    }

    // Mostrar dialog de logout
    fun showLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    // Fechar dialog
    fun dismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    // Fazer logout de verdade
    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                sessionRepository.clearToken()
                _uiState.update {
                    it.copy(
                        isLoggedOut = true,
                        showLogoutDialog = false,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Erro ao sair: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }
}