package com.example.rabisco.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.data.local.SessionViewModel
import com.example.rabisco.navigation.graphs.appGraph
import com.example.rabisco.navigation.graphs.authGraph
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    sessionViewModel: SessionViewModel = getKoin().get()
) {
    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsState()

    when (isLoggedIn) {
        null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        true -> {
            NavHost(
                navController = navController,
                startDestination = Routes.App.path
            ) {
                appGraph(navController)
            }
        }

        false -> {
            NavHost(
                navController = navController,
                startDestination = Routes.Auth.path
            ) {
                authGraph()
            }
        }
    }

}


