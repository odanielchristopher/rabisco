package com.example.rabisco.navigation.graphs

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.rabisco.navigation.Routes
import com.example.rabisco.ui.screens.home.HomeScreen
import com.example.rabisco.ui.screens.home.HomeViewModel
import com.example.rabisco.ui.screens.mytexts.MyTextsScreen
import com.example.rabisco.ui.screens.profile.ProfileScreen
import com.example.rabisco.ui.screens.stats.StatsScreen
import com.example.rabisco.ui.screens.write.WriteScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.appGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Routes.Home.path,
        route = Routes.App.path
    ) {
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
            ProfileScreen()
        }
    }
}
