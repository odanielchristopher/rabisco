package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.data.remote.dto.auth.response.AuthResponse
import com.example.rabisco.data.remote.services.AuthService
import com.example.rabisco.domain.repositories.AuthRepository
import kotlinx.coroutines.delay



class AuthRepositoryImpl(
    private val sessionRepository: SessionRepository,
    private val authService: AuthService
) : AuthRepository {
    override suspend fun signin(body: SignInDto): AuthResponse {
        // Versão MOCKADA (para testes)
        delay(1500) // simula atraso da rede

        val fakeResponse = AuthResponse(
            accessToken = "mocked-token-123",
        )

        sessionRepository.saveToken(fakeResponse.accessToken)
        return fakeResponse

        // val response = authService.signin(body)
        // sessionRepository.saveToken(response.accessToken)
        // return response
    }

    override suspend fun signup(body: SignUpDto): AuthResponse {
        // Versão MOCKADA (para testes)
        delay(1500)

        val fakeResponse = AuthResponse(
            accessToken = "mocked-token-abc",
        )

        sessionRepository.saveToken(fakeResponse.accessToken)
        return fakeResponse

        // val response = authService.signup(body)
        // sessionRepository.saveToken(response.accessToken)
        // return response
    }
}