package com.example.rabisco

import android.app.Application
import com.example.rabisco.core.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RabiscoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Logger do Koin (útil para debug)
            // Use Level.NONE em produção
            androidLogger(Level.DEBUG)

            // Contexto do Android
            androidContext(this@RabiscoApplication)

            // Carrega os módulos
            modules(
                AppModule
            )
        }
    }
}