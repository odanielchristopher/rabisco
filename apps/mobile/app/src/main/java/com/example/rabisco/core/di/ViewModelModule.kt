package com.example.rabisco.core.di

import com.example.rabisco.data.local.SessionViewModel
import com.example.rabisco.ui.screens.auth.AuthViewModel
import com.example.rabisco.ui.screens.home.HomeViewModel
import com.example.rabisco.ui.screens.mytexts.MyTextsViewModel
import com.example.rabisco.ui.screens.profile.ProfileViewModel
import com.example.rabisco.ui.screens.stats.StatsViewModel
import com.example.rabisco.ui.screens.write.WriteViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { WriteViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ProfileViewModel(androidContext(), get(), get()) }
    viewModel { MyTextsViewModel(get()) }
    viewModel { StatsViewModel(androidContext()) }
    single { SessionViewModel(get()) }
}