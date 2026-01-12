package com.example.rabisco.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.domain.repositories.PromptsRepository
import com.example.rabisco.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val promptsRepository: PromptsRepository
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
            try {
                setLoading(true)
                setError(null)

                // ✅ Busca tudo de uma vez: /users/me retorna TUDO!
                val userResult = userRepository.getMe()

                // Prompt do dia (em paralelo)
                val promptResult = promptsRepository.getDailyPrompt()

                // Atualiza UI com os dados obtidos
                userResult
                    .onSuccess { userData ->
                        val prompt = promptResult.getOrNull()

                        _uiState.update {
                            it.copy(
                                userName = userData.name,
                                streak = userData.totalDays,
                                totalXp = userData.score,
                                totalTexts = userData.totalTexts,
                                textsWeek = userData.textsThisWeek,
                                textsToday = userData.textsToday,
                                totalWords = userData.totalWord,
                                promptOfDay = prompt?.prompt ?: "Escreva sobre algo que te inspirou hoje.",
                                isLoading = false,
                                success = true
                            )
                        }
                    }
                    .onFailure { error ->
                        setError("Erro ao carregar dados: ${error.message}")
                        setLoading(false)

                        // Dados de fallback
                        _uiState.update {
                            it.copy(
                                userName = "Escritor",
                                promptOfDay = promptResult.getOrNull()?.prompt
                                    ?: "Escreva sobre algo que te inspirou hoje."
                            )
                        }
                    }

            } catch (e: Exception) {
                setError("Erro ao carregar dados: ${e.message}")
                setLoading(false)

                // Dados de fallback
                _uiState.update {
                    it.copy(
                        userName = "Escritor",
                        promptOfDay = "Escreva sobre algo que te inspirou hoje."
                    )
                }
            }
        }
    }

    fun refreshData() {
        loadHomeData()
    }
}