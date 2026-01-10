package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.dto.response.AuthResponse
import com.example.rabisco.data.remote.providers.ApiProvider
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signin")
    suspend fun signin(@Body body: SignInDto): AuthResponse

    @POST("auth/signup")
    suspend fun signup(@Body body: SignUpDto): AuthResponse
}

class AuthRepository(
    private val sessionRepository: SessionRepository
) {
    private val client = ApiProvider.provideUnauthenticated().create(AuthApi::class.java)

    suspend fun signin(body: SignInDto): Result<AuthResponse> {
        return try {
            val response = client.signin(body)
            sessionRepository.saveToken(response.accessToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(body: SignUpDto): Result<AuthResponse> {
        return try {
            val response = client.signup(body)
            sessionRepository.saveToken(response.accessToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        sessionRepository.clearSession()
    }
}