package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.services.AchievementsService
import com.example.rabisco.domain.repositories.AchievementsRepository
import com.example.rabisco.ui.screens.stats.Achievement
import org.koin.compose.getKoin

class AchievementsRepositoryImpl(
    private val achievementsService: AchievementsService
) : AchievementsRepository {

    override suspend fun getAllAchievements(): Result<List<Achievement>> {
        return try {
            val response = achievementsService.getAll()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar conquistas: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
