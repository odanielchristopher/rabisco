package com.example.rabisco.data.remote.services

import com.example.rabisco.domain.models.Prompt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PromptsService {
    @GET("/prompts/daily")
    suspend fun getDailyPrompt(): Response<Prompt>

    @GET("/prompts/{id}")
    suspend fun getPromptById(@Path("id") promptId: String): Response<Prompt>
}