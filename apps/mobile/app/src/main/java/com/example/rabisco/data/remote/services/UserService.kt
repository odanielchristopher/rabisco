package com.example.rabisco.data.remote.services

import com.example.rabisco.data.remote.dto.users.request.UpdateUserDto
import com.example.rabisco.data.remote.dto.users.response.MeResponse
import com.example.rabisco.data.remote.dto.users.response.UpdateUserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/users/me")
    suspend fun me(): Response<MeResponse>

    @POST("/users/edit-me")
    suspend fun update(body: UpdateUserDto): Response<UpdateUserResponse>

    @DELETE("/users/delete-me")
    suspend fun remove()
}