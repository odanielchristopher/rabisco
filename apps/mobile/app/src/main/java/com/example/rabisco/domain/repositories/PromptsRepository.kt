package com.example.rabisco.domain.repositories

import com.example.rabisco.domain.models.Prompt

interface PromptsRepository {
    suspend fun getDailyPrompt(): Result<Prompt>
    suspend fun getPromptById(promptId: String): Result<Prompt>
}