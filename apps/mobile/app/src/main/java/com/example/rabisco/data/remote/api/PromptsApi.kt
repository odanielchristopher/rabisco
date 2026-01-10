package com.example.rabisco.data.remote.api

import com.example.rabisco.data.remote.dto.response.DailyPromptResponse
import retrofit2.http.*

interface PromptsApi {
    @GET("prompts/daily")
    suspend fun getDailyPrompt(): DailyPromptResponse

    @GET("prompts/{id}")
    suspend fun getPromptById(@Path("id") id: String): DailyPromptResponse
}