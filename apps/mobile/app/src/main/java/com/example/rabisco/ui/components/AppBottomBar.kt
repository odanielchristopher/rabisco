package com.example.rabisco.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rabisco.navigation.Routes

@Composable
fun AppBottomBar(navController: NavController, currentRoute: String?, showElements: Boolean = true) {
    if (!showElements) return;

    fun navigate(route: Routes) {
        if (currentRoute == route.path) return

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

        NavigationBarItem(
            label = { Text("Progresso", fontSize = 10.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.BarChart,
                    contentDescription = "Progresso"
                )
            },
            selected = false,
            onClick = { navigate(Routes.Stats)},
        )

        NavigationBarItem(
            label = { Text("Histórico", fontSize = 10.sp) },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                    contentDescription = "Histórico"
                )
            },
            selected = false,
            onClick = { navigate(Routes.MyTexts)},
        )

        NavigationBarItem(
            label = { Text("Perfil", fontSize = 10.sp) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Perfil"
                )
            },
            selected = false,
            onClick = { navigate(Routes.Profile)},
        )
    }
}