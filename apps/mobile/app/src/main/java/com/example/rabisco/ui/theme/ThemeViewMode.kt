package com.example.rabisco.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.local.SessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// ViewModel pra gerenciar o tema do app
class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionRepository = SessionRepository(application)

    // Observar mudanças no dark mode
    val isDarkMode: StateFlow<Boolean> = sessionRepository.observeDarkMode()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    // Mudar o tema
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            sessionRepository.saveDarkMode(enabled)
        }
    }
}