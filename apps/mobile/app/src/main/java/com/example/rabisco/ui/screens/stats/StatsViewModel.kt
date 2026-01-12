package com.example.rabisco.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.domain.repositories.AchievementsRepository
import com.example.rabisco.domain.repositories.DailyMissionsRepository
import com.example.rabisco.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(
    private val userRepository: UserRepository,
    private val achievementsRepository: AchievementsRepository,
    private val dailyMissionsRepository: DailyMissionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val userResult = userRepository.getMe()
                val achievementsResult = achievementsRepository.getAllAchievements()
                val missionsResult = dailyMissionsRepository.getAllDailyMissions()

                userResult.onSuccess { userData ->
                    val achievements = achievementsResult.getOrNull()?.map { it.toUiModel() } ?: emptyList()
                    val missions = missionsResult.getOrNull()?.map { it.toUiModel() } ?: emptyList()

                    _uiState.update {
                        it.copy(
                            textsWritten = userData.totalTexts,
                            totalXp = userData.score,
                            textsToday = userData.textsToday,
                            textsGoal = 2, // Meta fixa
                            streak = userData.totalDays,
                            achievements = achievements,
                            dailyMissions = missions,
                            isLoading = false
                        )
                    }
                }

                userResult.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMessage = "Erro ao carregar estatísticas: ${error.message}",
                            isLoading = false
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Erro ao carregar dados: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun refreshStats() {
        loadStats()
    }
}