package com.example.rabisco.data.remote.api

import com.example.rabisco.data.remote.dto.request.CreateTagDto
import com.example.rabisco.data.remote.dto.response.TagResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TagsApi {
    @GET("tags")
    suspend fun getAllTags(): List<TagResponse>

    @GET("tags/{id}")
    suspend fun getTagById(@Path("id") id: String): TagResponse

    @POST("tags")
    suspend fun createTag(@Body body: CreateTagDto): TagResponse

    @PUT("tags/{id}")
    suspend fun updateTag(@Path("id") id: String, @Body body: CreateTagDto): TagResponse

    @DELETE("tags/{id}")
    suspend fun deleteTag(@Path("id") id: String)
}