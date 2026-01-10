package com.example.rabisco.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.ui.screens.auth.AuthScreen
import com.example.rabisco.ui.screens.write.WriteScreen
import com.example.rabisco.ui.screens.home.HomeScreen
import com.example.rabisco.ui.screens.home.HomeUiState
import com.example.rabisco.ui.screens.home.HomeViewModel
import com.example.rabisco.ui.screens.mytexts.MyTextsScreen
import com.example.rabisco.ui.screens.mytexts.MyTextsViewModel
import com.example.rabisco.ui.screens.profile.ProfileScreen  // ✅ IMPORT
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import com.example.rabisco.ui.screens.stats.StatsScreen
import com.example.rabisco.ui.screens.stats.StatsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    sessionRepository: SessionRepository = koinInject()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Auth.path
    ) {
        composable(Routes.Auth.path) {
            AuthScreen(navigator = navController)
        }

        composable(Routes.Home.path) {
            val homeViewModel: HomeViewModel = koinViewModel()
            val uiState by homeViewModel.uiState.collectAsState()

            HomeScreen(
                uiState = uiState,
                onNavigateToWrite = { type ->
                    navController.navigate(Routes.Write.createRoute(type = type))
                }
            )
        }

        composable(Routes.Write.path) {
            WriteScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Routes.MyTexts.path) {
            MyTextsScreen(
                onNavigateToWrite = {
                    navController.navigate(Routes.Write.createRoute(type = "free"))
                },
            )
        }

        composable(Routes.Stats.path) {
            StatsScreen()
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

