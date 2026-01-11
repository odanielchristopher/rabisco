package com.example.rabisco.domain.repositories

import com.example.rabisco.ui.screens.stats.DailyMission

interface DailyMissionsRepository {
    suspend fun getAllDailyMissions(): Result<List<DailyMission>>
}