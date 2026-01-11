package com.example.rabisco.navigation

sealed class Routes(val path: String) {

    data object Auth : Routes("auth")
    data object App : Routes("app")

    // Rotas protegidas (após login)
    data object Home : Routes("home")
    data object MyTexts : Routes("mytexts")
    data object Stats : Routes("stats")
    data object Profile : Routes("profile")

    data object Write : Routes("write?type={type}&textId={textId}&mode={mode}") {
        fun createRoute(
            type: String = "free",
            textId: String? = null,
            mode: String = "create"
        ): String {
            return "write?type=$type&textId=${textId ?: ""}&mode=$mode"
        }
    }

}