package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.data.remote.dto.auth.response.AuthResponse
import com.example.rabisco.data.remote.services.AuthService
import com.example.rabisco.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun signin(body: SignInDto): Result<AuthResponse> {
        return try {
            val response = authService.signin(body)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao autenticar usuário: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(body: SignUpDto): Result<AuthResponse> {
        return try {
            val response = authService.signup(body)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao criar usuário: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}