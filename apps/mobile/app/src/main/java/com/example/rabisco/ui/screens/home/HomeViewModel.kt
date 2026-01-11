package com.example.rabisco.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    private fun setError(message: String?) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)
                _uiState.update { it.copy(
                    streak = 5,
                    totalXp = 1250,
                    totalTexts = 12,
                    textsWeek = 3,
                    promptOfDay = "prompt genérico para teste do viewmodel...",
                    isLoading = false
                ) }
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
}