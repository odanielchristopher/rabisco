package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.services.PromptsService
import com.example.rabisco.domain.models.Prompt
import com.example.rabisco.domain.repositories.PromptsRepository

class PromptsRepositoryImpl(
    private val promptsService: PromptsService
) : PromptsRepository {

    override suspend fun getDailyPrompt(): Result<Prompt> {
        return try {
            val response = promptsService.getDailyPrompt()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar prompt diário: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPromptById(promptId: String): Result<Prompt> {
        return try {
            val response = promptsService.getPromptById(promptId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar prompt: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}