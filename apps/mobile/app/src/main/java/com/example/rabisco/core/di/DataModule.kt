package com.example.rabisco.core.di

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.services.AuthService
import com.example.rabisco.data.remote.repositories.AuthRepositoryImpl
import com.example.rabisco.data.remote.repositories.TextRepositoryImpl
import com.example.rabisco.data.remote.services.AchievementsService
import com.example.rabisco.data.remote.services.DailyMissionsService
import com.example.rabisco.data.remote.services.TagsService
import com.example.rabisco.data.remote.services.TextsService
import com.example.rabisco.data.remote.services.UserService
import com.example.rabisco.domain.repositories.AuthRepository
import com.example.rabisco.domain.repositories.TextRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val DataModule = module {

    // SessionRepository (gerencia token no DataStore)
    single { SessionRepository(androidContext()) }

    // ---- Services ----

    // AuthService (usa Retrofit não autenticado)
    single {
        get<Retrofit>(named("unauthenticated")).create(AuthService::class.java)
    }

    // Services com autenticação
    single {
        get<Retrofit>(named("authenticated")).create(UserService::class.java)
    }
    single {
        get<Retrofit>(named("authenticated")).create(AchievementsService::class.java)
    }
    single {
        get<Retrofit>(named("authenticated")).create(DailyMissionsService::class.java)
    }
    single {
        get<Retrofit>(named("authenticated")).create(TagsService::class.java)
    }
    single {
        get<Retrofit>(named("authenticated")).create(TextsService::class.java)
    }


    // ---- Repositories ----
    single<AuthRepository> { AuthRepositoryImpl(get(), get())}
    single<TextRepository> { TextRepositoryImpl() }

}