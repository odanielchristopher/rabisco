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
import com.example.rabisco.ui.screens.auth.AuthScreen
import com.example.rabisco.ui.screens.write.WriteScreen
import com.example.rabisco.ui.screens.home.HomeScreen
import com.example.rabisco.ui.screens.home.HomeUiState
import com.example.rabisco.ui.screens.home.HomeViewModel
import com.example.rabisco.ui.screens.mytexts.MyTextsScreen
import com.example.rabisco.ui.screens.mytexts.MyTextsViewModel
import com.example.rabisco.ui.screens.profile.ProfileScreen  // ✅ IMPORT
import com.example.rabisco.ui.screens.stats.StatsScreen
import com.example.rabisco.ui.screens.stats.StatsViewModel

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
            val context = LocalContext.current
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(context)
            )
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
            val context = LocalContext.current
            MyTextsScreen(
                onNavigateToWrite = {
                    navController.navigate(Routes.Write.createRoute(type = "free"))
                },
                viewModel = viewModel(
                    factory = MyTextsViewModel.provideFactory(context)
                )
            )
        }

        composable(Routes.Stats.path) {
            val context = LocalContext.current
            StatsScreen(
                viewModel = viewModel(
                    factory = StatsViewModel.provideFactory(context)
                )
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

