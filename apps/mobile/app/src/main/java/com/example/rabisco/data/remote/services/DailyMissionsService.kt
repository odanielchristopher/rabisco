package com.example.rabisco.data.remote.services

import com.example.rabisco.ui.screens.stats.DailyMission
import retrofit2.http.GET

interface DailyMissionsService {
    @GET("/missions/daily")
    suspend fun getAll(): List<DailyMission>
}