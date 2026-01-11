package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.services.DailyMissionsService
import com.example.rabisco.domain.repositories.DailyMissionsRepository
import com.example.rabisco.ui.screens.stats.DailyMission

class DailyMissionsRepositoryImpl(
    private val dailyMissionsService: DailyMissionsService
) : DailyMissionsRepository {

    override suspend fun getAllDailyMissions(): Result<List<DailyMission>> {
        return try {
            val missions = dailyMissionsService.getAll()
            Result.success(missions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}