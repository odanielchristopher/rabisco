package com.example.rabisco.navigation

sealed class Routes(val path: String) {

    data object Auth : Routes("auth")

    // Sub-rotas internas do Auth
    data object SignIn : Routes("signin")
    data object SignUp : Routes("signup")

    // Rotas protegidas (após login)
    data object Home : Routes("home")
    data object MyTexts : Routes("mytexts")
    data object Stats : Routes("stats")
    data object Write : Routes("write")
    data object Profile : Routes("profile")
    data object Settings : Routes("settings")
}