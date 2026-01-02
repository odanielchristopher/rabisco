package com.example.rabisco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.ui.screens.auth.AuthScreen
import com.example.rabisco.ui.screens.write.WriteScreen
import com.example.rabisco.ui.screens.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Auth.path
    ) {
        composable(Routes.Auth.path) {
            AuthScreen(navigator = navController)
        }

        composable(Routes.Home.path) {
            WriteScreen()
        }
    }
}