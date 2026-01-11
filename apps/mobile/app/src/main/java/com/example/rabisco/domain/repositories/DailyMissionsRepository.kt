package com.example.rabisco.domain.repositories

import com.example.rabisco.domain.models.DailyMission

interface DailyMissionsRepository {
    suspend fun getAllDailyMissions(): Result<List<DailyMission>>
}