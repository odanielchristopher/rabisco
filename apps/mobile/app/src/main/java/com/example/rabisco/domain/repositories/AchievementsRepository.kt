package com.example.rabisco.domain.repositories

import com.example.rabisco.ui.screens.stats.Achievement

interface AchievementsRepository {
    suspend fun getAllAchievements(): Result<List<Achievement>>
}