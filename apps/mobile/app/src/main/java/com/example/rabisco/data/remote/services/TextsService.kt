package com.example.rabisco.data.remote.services

import com.example.rabisco.data.remote.dto.texts.request.CreateTextDto
import com.example.rabisco.data.remote.dto.texts.request.UpdateTextDto
import com.example.rabisco.domain.models.Text
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TextsService {

    @GET("/texts")
    suspend fun getAll(@Query("category") category: String?, @Query("search") search: String?): Response<List<Text>>

    @GET("/texts/{textId}")
    suspend fun getOne(
        @Path("textId") textId: String
    ): Response<Text>

    @POST("/texts")
    suspend fun create(
        @Body createTextDto: CreateTextDto
    ): Response<Text>

    @PUT("/texts/{textId}")
    suspend fun update(
        @Path("textId") textId: String,
        @Body updateTextDto: UpdateTextDto
    ): Response<Text>

    @DELETE("/texts/{textId}")
    suspend fun remove(
        @Path("textId") textId: String
    )
}