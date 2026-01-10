package com.example.rabisco.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.remote.repositories.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

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
            setLoading(true)
            setError(null)

            homeRepository.getHomeData()
                .onSuccess { homeData ->
                    _uiState.update {
                        it.copy(
                            streak = homeData.streak,
                            totalXp = homeData.totalXP,
                            totalTexts = homeData.totalTexts,
                            textsWeek = homeData.textsWeek,
                            promptOfDay = homeData.promptOfDay,
                            isLoading = false,
                            success = true
                        )
                    }

                    println("✅ Home data loaded successfully!")
                    println("   Streak: ${homeData.streak} dias")
                    println("   Total XP: ${homeData.totalXP}")
                    println("   Textos: ${homeData.totalTexts} (${homeData.textsWeek} esta semana)")
                }
                .onFailure { exception ->
                    println("HomeViewModel Error: ${exception.message}")
                    setError("Erro ao carregar dados: ${exception.message}")


                    _uiState.update {
                        it.copy(
                            streak = 5,
                            totalXp = 1250,
                            totalTexts = 12,
                            textsWeek = 3,
                            promptOfDay = "Se você pudesse viajar para qualquer lugar no tempo, para onde iria?",
                            isLoading = false,
                            success = false
                        )
                    }

                    println("mock fallback")
                }
        }
    }

    fun refreshPrompt() {
        viewModelScope.launch {
            homeRepository.getDailyPrompt()
                .onSuccess { newPrompt ->
                    _uiState.update { it.copy(promptOfDay = newPrompt) }
                    println("Prompt refreshed: $newPrompt")
                }
                .onFailure { exception ->
                    println("Error refreshing prompt: ${exception.message}")
                }
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val homeRepository = HomeRepository(context)
                    return HomeViewModel(homeRepository) as T
                }
            }
    }
}