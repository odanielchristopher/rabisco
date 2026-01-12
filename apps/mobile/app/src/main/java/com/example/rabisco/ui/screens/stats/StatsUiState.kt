package com.example.rabisco.ui.screens.stats

data class StatsUiState(
    val textsWritten: Int = 0,
    val totalXp: Int = 0,
    val textsToday: Int = 0,
    val textsGoal: Int = 150,
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
