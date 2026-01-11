package com.example.rabisco.domain.repositories

import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.data.remote.dto.auth.response.AuthResponse

interface AuthRepository {
    suspend fun signin(body: SignInDto): AuthResponse
    suspend fun signup(body: SignUpDto): AuthResponse
}