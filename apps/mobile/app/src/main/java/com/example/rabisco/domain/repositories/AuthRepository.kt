package com.example.rabisco.domain.repositories

import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.dto.response.AuthResponse

interface AuthRepository {
    suspend fun signin(body: SignInDto): AuthResponse
    suspend fun signup(body: SignUpDto): AuthResponse
}