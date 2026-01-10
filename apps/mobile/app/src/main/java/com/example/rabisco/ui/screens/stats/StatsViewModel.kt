package com.example.rabisco.ui.screens.stats

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StatsViewModel(private val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // dados mockados pra testar
            val achievements = listOf(
                Achievement(
                    title = "Primeira Palavra",
                    description = "Escreva seu primeiro texto",
                    progress = 1f,
                    progressText = "1/1 textos",
                    rewardText = "Recompensa: +10 XP",
                    iconName = "target",
                    isCompleted = true
                ),
                Achievement(
                    title = "100 Palavras",
                    description = "Escreva um texto com 100+ palavras",
                    progress = 0f,
                    progressText = "0/100 palavras",
                    rewardText = "Recompensa: +50 XP",
                    iconName = "description",
                    isCompleted = false
                ),
                Achievement(
                    title = "Escritor Dedicado",
                    description = "Mantenha uma ofensiva de 7 dias",
                    progress = 1f,
                    progressText = "7/7 dias",
                    rewardText = "Recompensa: +100 XP",
                    iconName = "fire",
                    isCompleted = true
                ),
                Achievement(
                    title = "Maratonista",
                    description = "Escreva 10 textos em um mês",
                    progress = 0.4f,
                    progressText = "4/10 textos",
                    rewardText = "Recompensa: +200 XP",
                    iconName = "trophy",
                    isCompleted = false
                )
            )

            val missions = listOf(
                DailyMission(
                    title = "Escreva seu primeiro texto",
                    description = "Comece o dia escrevendo!",
                    progress = 1f,
                    progressText = "1/1 texto",
                    rewardText = "+50 XP",
                    iconName = "edit",
                    renovaEm = "13h",
                    isCompleted = true
                ),
                DailyMission(
                    title = "Complete o prompt do dia",
                    description = "Responda ao desafio criativo de hoje",
                    progress = 0f,
                    progressText = "0/1 prompt",
                    rewardText = "+80 XP",
                    iconName = "auto_awesome",
                    renovaEm = "13h",
                    isCompleted = false
                ),
                DailyMission(
                    title = "Escreva 200 palavras",
                    description = "Alcance a meta de palavras hoje",
                    progress = 0f,
                    progressText = "0/200 palavras",
                    rewardText = "+100 XP",
                    iconName = "book",
                    renovaEm = "13h",
                    isCompleted = false
                )
            )

            _uiState.update {
                it.copy(
                    textsWritten = 42,
                    totalXp = 1250,
                    xpToday = 50,
                    xpGoal = 150,
                    streak = 7,
                    achievements = achievements,
                    dailyMissions = missions,
                    isLoading = false
                )
            }
        }
    }

    fun refreshStats() {
        loadStats()
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return StatsViewModel(context) as T
                }
            }
        }
    }
}