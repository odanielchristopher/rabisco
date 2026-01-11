package com.example.rabisco.core.di

import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.repositories.*
import com.example.rabisco.data.remote.services.*
import com.example.rabisco.domain.repositories.*
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
    single<AuthRepository> { AuthRepositoryImpl(get())}

    single<TextRepository> { TextRepositoryImpl() } // LOCAL (PARA TESTES)
    single<TextsRepository>{ TextsRepositoryImpl(get()) } // REMOTO (CONECTADO COM A API)

    single<UserRepository>{ UserRepositoryImpl(get()) }
    single<PromptsRepository> { PromptsRepositoryImpl(get()) }
    single<TagsRepository> { TagsRepositoryImpl(get()) }
    single<AchievementsRepository> { AchievementsRepositoryImpl(get()) }
    single<DailyMissionsRepository> { DailyMissionsRepositoryImpl(get()) }

}