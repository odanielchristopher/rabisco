package com.example.rabisco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.ui.screens.auth.AuthScreen
import com.example.rabisco.ui.screens.write.WriteScreen
import com.example.rabisco.ui.screens.home.HomeScreen
import com.example.rabisco.ui.screens.mytexts.MyTextsScreen
import com.example.rabisco.ui.screens.profile.ProfileScreen  // ✅ IMPORT

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Profile.path
    ) {
        composable(Routes.Auth.path) {
            AuthScreen(navigator = navController)
        }

        composable(Routes.Home.path) {
            MyTextsScreen(
                onNavigateToWrite = { navController.navigate(Routes.Write.path) }
            )
        }

        composable(Routes.Write.path) {
            WriteScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Profile.path) {
            ProfileScreen(
                onNavigateToAuth = {
                    navController.navigate(Routes.Auth.path) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}