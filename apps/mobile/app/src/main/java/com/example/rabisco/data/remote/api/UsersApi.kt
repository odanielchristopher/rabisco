package com.example.rabisco.data.remote.api

import com.example.rabisco.data.remote.dto.request.UpdateUserDto
import com.example.rabisco.data.remote.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface UsersApi {
    @GET("users/me")
    suspend fun getMe(): UserResponse

    @PUT("users/edit-me")
    suspend fun updateMe(@Body body: UpdateUserDto): UserResponse

    @DELETE("users/delete-me")
    suspend fun deleteMe()
}