package com.example.rabisco.data.remote.services

import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.dto.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun signin(@Body body: SignInDto): AuthResponse

    @POST("auth/signup")
    suspend fun signup(@Body body: SignUpDto): AuthResponse
}