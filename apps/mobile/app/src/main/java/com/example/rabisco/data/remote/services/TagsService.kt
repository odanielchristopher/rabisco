package com.example.rabisco.data.remote.services

import com.example.rabisco.domain.models.Tag
import retrofit2.Response
import retrofit2.http.GET

interface TagsService {
    @GET("/tags")
    suspend fun getAll(): Response<List<Tag>>
}