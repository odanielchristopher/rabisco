package com.example.rabisco.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.ui.theme.screens.mytexts.MyTextsScreen
import com.example.rabisco.ui.theme.screens.write.WriteScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "my_texts"
    ) {
        composable("my_texts") {
            MyTextsScreen(
                onNavigateToWrite = {
                    navController.navigate("write")
                }
            )
        }
        composable("write") {
            WriteScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}

