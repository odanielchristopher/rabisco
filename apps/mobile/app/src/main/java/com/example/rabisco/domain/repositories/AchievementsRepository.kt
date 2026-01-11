package com.example.rabisco.domain.repositories

import com.example.rabisco.domain.models.Achievement

interface AchievementsRepository {
    suspend fun getAllAchievements(): Result<List<Achievement>>
}