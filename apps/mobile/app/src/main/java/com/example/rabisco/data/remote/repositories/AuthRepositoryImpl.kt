package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.dto.response.AuthResponse
import com.example.rabisco.data.remote.services.AuthService
import com.example.rabisco.domain.repositories.AuthRepository
import kotlinx.coroutines.delay



class AuthRepositoryImpl(
    private val sessionRepository: SessionRepository,
    // Agora recebemos a API via construtor (injetada pelo Koin)
     private val authApi: AuthService // <- Descomente quando quiser usar a API real
) : AuthRepository {
    override suspend fun signin(body: SignInDto): AuthResponse {
        // Versão MOCKADA (para testes)
        delay(1500) // simula atraso da rede

        val fakeResponse = AuthResponse(
            accessToken = "mocked-token-123",
        )

        sessionRepository.saveToken(fakeResponse.accessToken)
        return fakeResponse

        // Versão REAL (descomente quando o backend estiver pronto)
        // val response = authApi.signin(body)
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

        // Versão REAL (descomente quando o backend estiver pronto)
        // val response = authApi.signup(body)
        // sessionRepository.saveToken(response.accessToken)
        // return response
    }
}