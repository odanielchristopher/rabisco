package com.example.rabisco.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppBottomBar(navController: NavController, showElements: Boolean = true) {
    if (!showElements) return;

    fun navigate(route: Routes) {
        navController.navigate(route.path)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            label = { Text("Home", fontSize = 10.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            selected = false,
            onClick = { navigate(Routes.Home)},
        )
    }
}