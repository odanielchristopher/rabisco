package com.example.rabisco.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.local.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {

    private val sessionRepository = SessionRepository(context)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadDarkModePreference()
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
                sessionRepository.clearSession()
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

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProfileViewModel(context) as T
                }
            }
    }
}