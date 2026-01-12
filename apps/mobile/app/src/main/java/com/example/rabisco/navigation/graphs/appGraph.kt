package com.example.rabisco.navigation.graphs

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            HomeScreen(
                onNavigateToWrite = { type ->
                    navController.navigate(Routes.Write.createRoute(type = type))
                }
            )
        }
        composable(
            route = Routes.Write.path,
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                    defaultValue = "free"
                },
                navArgument("textId") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("mode") {
                    type = NavType.StringType
                    defaultValue = "create"
                }
            )
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "free"
            val textId = backStackEntry.arguments?.getString("textId")?.takeIf { it.isNotBlank() }
            val mode = backStackEntry.arguments?.getString("mode") ?: "create"

            WriteScreen(
                onNavigateBack = { navController.popBackStack() },
                onTextSaved = { navController.popBackStack() },
                textId = textId,
                mode = mode
            )
        }

        composable(Routes.MyTexts.path) {
            MyTextsScreen(
                onNavigateToWrite = {
                    navController.navigate(Routes.Write.createRoute(type = "free"))
                },
                onNavigateToEdit = { textId ->
                    navController.navigate(
                        Routes.Write.createRoute(
                            type = "free",
                            textId = textId,
                            mode = "edit"
                        )
                    )
                },
                onNavigateToView = { textId ->
                    navController.navigate(
                        Routes.Write.createRoute(
                            type = "free",
                            textId = textId,
                            mode = "view"
                        )
                    )
                }
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