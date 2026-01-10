package com.example.rabisco.data.remote.providers

import android.content.Context
import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    // Retrofit autenticado (usa token)
    fun provideAuthenticated(context: Context): Retrofit {
        val sessionRepo = SessionRepository(context)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { sessionRepo.getToken() })
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://rabisco-b1tb.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // Retrofit que NÃO envia token (login & signup)
    fun provideUnauthenticated(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://rabisco-b1tb.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}