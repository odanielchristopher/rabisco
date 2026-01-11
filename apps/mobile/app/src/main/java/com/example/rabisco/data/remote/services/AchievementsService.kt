package com.example.rabisco.data.remote.services

import com.example.rabisco.ui.screens.stats.Achievement
import retrofit2.Response
import retrofit2.http.GET

interface AchievementsService {
    @GET("/achievements")
    suspend fun getAll(): Response<List<Achievement>>
}