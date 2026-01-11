package com.example.rabisco.data.remote.interceptors

import com.example.rabisco.data.local.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenExpirationInterceptor(
    private val sessionRepository: SessionRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)


        if (response.code == 401) {
            // Limpa o token para forçar logout
            runBlocking {
                sessionRepository.clearToken()
            }
        }

        return response
    }
}
