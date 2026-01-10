package com.example.rabisco.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rabisco.navigation.Routes
import com.example.rabisco.ui.components.AppButton
import com.example.rabisco.ui.screens.auth.components.AuthInput
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    navigator: NavHostController,
    vm: AuthViewModel = koinViewModel()
) {
    val state by vm.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) {
            navigator.navigate(Routes.Home.path) {
                popUpTo(Routes.Auth.path) { inclusive = true }
            }
        }
    }

    Column {
        AuthInput("Nome", name, { name = it })
        AuthInput("E-mail", email, { email = it })
        AuthInput("Senha", password, { password = it })

        Spacer(Modifier.height(12.dp))

        AppButton(
            text = "Cadastrar",
            onClick = { vm.signup(name, email, password) },
            loading = state.loading,
            modifier = Modifier.fillMaxWidth()
        )

        state.error?.let {
            Text(it, color = Color.Red)
        }
    }
}