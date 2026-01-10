package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.dto.response.AuthResponse
import com.example.rabisco.data.remote.providers.ApiProvider
import kotlinx.coroutines.delay
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

    suspend fun signin(body: SignInDto): AuthResponse {
//        val response = this.client.signin(body)
//        sessionRepository.saveToken(response.accessToken)
//        return response;
        delay(1500) // simula atraso da rede

        val fakeResponse = AuthResponse(
            accessToken = "mocked-token-123",
        )

        sessionRepository.saveToken(fakeResponse.accessToken)

        return fakeResponse
    }

    suspend fun signup(body: SignUpDto): AuthResponse {
//        val response = this.client.signup(body)
//        sessionRepository.saveToken(response.accessToken);
//        return response;
        delay(1500)

        val fakeResponse = AuthResponse(
            accessToken = "mocked-token-abc",
        )

        sessionRepository.saveToken(fakeResponse.accessToken)

        return fakeResponse
    }
}