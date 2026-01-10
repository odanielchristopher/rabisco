package com.example.rabisco.data.remote.api

import com.example.rabisco.data.remote.dto.request.CreateTextDto
import com.example.rabisco.data.remote.dto.request.UpdateTextDto
import com.example.rabisco.data.remote.dto.response.TextResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TextsApi {
    @GET("texts")
    suspend fun getAllTexts(
        @Query("category") category: String? = null,
        @Query("search") search: String? = null
    ): List<TextResponse>

    @GET("texts/{textId}")
    suspend fun getTextById(@Path("textId") textId: String): TextResponse

    @POST("texts")
    suspend fun createText(@Body body: CreateTextDto): TextResponse

    @PUT("texts/{textId}")
    suspend fun updateText(@Path("textId") textId: String, @Body body: UpdateTextDto): TextResponse

    @DELETE("texts/{textId}")
    suspend fun deleteText(@Path("textId") textId: String)
}