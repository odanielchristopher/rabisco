package com.example.rabisco.ui.screens.stats

data class StatsUiState(
    val textsWritten: Int = 0,
    val totalXp: Int = 0,
    val xpToday: Int = 0,
    val xpGoal: Int = 150,
    val streak: Int = 0,
    val achievements: List<Achievement> = emptyList(),
    val dailyMissions: List<DailyMission> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class Achievement(
    val title: String,
    val description: String,
    val progress: Float,
    val progressText: String,
    val rewardText: String,
    val iconName: String,
    val isCompleted: Boolean = false
)

data class DailyMission(
    val title: String,
    val description: String,
    val progress: Float,
    val progressText: String,
    val rewardText: String,
    val iconName: String,
    val renovaEm: String,
    val isCompleted: Boolean = false
)