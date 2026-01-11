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

data class StatsUiState(
    val textsWritten: Int = 0,
    val totalXp: Int = 0,
    val xpToday: Int = 0,
    val xpGoal: Int = 150,
    val streak: Int = 0,
    val achievements: List<UiAchievement> = emptyList(),
    val dailyMissions: List<UiDailyMission> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class UiAchievement(
    val title: String,
    val description: String,
    val progress: Float,
    val progressText: String,
    val rewardText: String,
    val iconName: String,
    val isCompleted: Boolean = false
)

data class UiDailyMission(
    val title: String,
    val description: String,
    val progress: Float,
    val progressText: String,
    val rewardText: String,
    val iconName: String,
    val renovaEm: String,
    val isCompleted: Boolean = false
)

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

                    val xpToday = calculateXpToday(userData.textsToday)

                    _uiState.update {
                        it.copy(
                            textsWritten = userData.totalTexts,
                            totalXp = userData.score,
                            xpToday = xpToday,
                            xpGoal = 150, // Meta fixa
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

    private fun calculateXpToday(textsToday: Int): Int {
        // XP = textos hoje * 50 (50 XP por texto)
        // Você pode ajustar essa fórmula conforme suas regras de XP
        return textsToday * 50
    }

    fun refreshStats() {
        loadStats()
    }
}