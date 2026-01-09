package com.example.rabisco.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.ui.components.AppBottomBar
import com.example.rabisco.navigation.AppNavHost
import com.example.rabisco.navigation.Routes
import com.example.rabisco.ui.components.AppAddButton
import com.example.rabisco.ui.components.WriteTypeBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showBottomSheet by remember { mutableStateOf(false) }

    val showNavigationElements = currentRoute in listOf(
        Routes.Home.path,
        Routes.MyTexts.path,
        Routes.Profile.path,
        Routes.Stats.path
    )

    fun handleShowBottomSheet(value: Boolean) {
        if (showBottomSheet == value) return
        showBottomSheet = value;
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(navController = navController, currentRoute = currentRoute, showElements = showNavigationElements)
        },
        floatingActionButton = {
            if (showNavigationElements) {
                AppAddButton(
                    onClick = {
                        handleShowBottomSheet(true)
                    },
                    verticalOffset = 60.dp
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(navController = navController)
        }
        if (showBottomSheet) {
            WriteTypeBottomSheet(
                onDismiss = { handleShowBottomSheet(false) },
                onSelectType = { type ->
                    handleShowBottomSheet(false)
                    navController.navigate(Routes.Write.createRoute(type))
                }
            )
        }
    }
}