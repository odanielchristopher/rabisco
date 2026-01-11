package com.example.rabisco.data.remote.services

import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.data.remote.dto.auth.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun signin(@Body body: SignInDto): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signup(@Body body: SignUpDto): Response<AuthResponse>
}