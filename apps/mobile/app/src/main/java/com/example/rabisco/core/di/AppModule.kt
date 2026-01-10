package com.example.rabisco.core.di

import org.koin.dsl.module

/**
 * AppModule - Módulo principal que agrega todos os outros módulos da aplicação
 *
 * Este módulo centraliza todas as dependências do app:
 * - networkModule: Retrofit, OkHttp, APIs
 * - dataModule: Repositories, DataSources, DataStore
 * - viewModelModule: Todos os ViewModels
 */
val AppModule = module {
    includes(
        NetworkModule,
        DataModule,
        ViewModelModule
    )
}