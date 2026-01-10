package com.example.rabisco.core.di

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NetworkModule = module {

    // Base URL
    single(named("baseUrl")) { "https://rabisco-b1tb.onrender.com/" }

    // Logging Interceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Auth Interceptor (usa SessionRepository para pegar o token)
    single {
        AuthInterceptor { get<SessionRepository>().getToken() }
    }

    // OkHttpClient AUTENTICADO (com token)
    single(named("authenticated")) {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // OkHttpClient NÃO AUTENTICADO (sem token - para login/signup)
    single(named("unauthenticated")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Retrofit AUTENTICADO (para chamadas que precisam de token)
    single(named("authenticated")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named("authenticated")))
            .build()
    }

    // Retrofit NÃO AUTENTICADO (para login/signup)
    single(named("unauthenticated")) {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .addConverterFactory(GsonConverterFactory.create())
            .client(get(named("unauthenticated")))
            .build()
    }
}