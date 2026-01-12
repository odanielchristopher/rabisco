package com.example.rabisco.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.rabisco.navigation.Routes
import com.example.rabisco.ui.screens.auth.AuthScreen

fun NavGraphBuilder.authGraph() {
    composable(Routes.Auth.path) {
        AuthScreen()
    }
}