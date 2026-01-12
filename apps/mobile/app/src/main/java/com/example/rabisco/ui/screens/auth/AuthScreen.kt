package com.example.rabisco.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.data.local.SessionViewModel
import com.example.rabisco.ui.components.Container
import com.example.rabisco.ui.components.ToastMessage
import com.example.rabisco.ui.screens.auth.components.AuthTabSelector
import com.example.rabisco.ui.screens.auth.components.FooterSection
import com.example.rabisco.ui.screens.auth.components.HeaderSection
import com.example.rabisco.ui.screens.auth.components.SignInForm
import com.example.rabisco.ui.screens.auth.components.SignUpForm
import com.example.rabisco.ui.theme.RabiscoTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = koinViewModel(),
    sessionViewModel: SessionViewModel = getKoin().get()
) {
    val authUiState by authViewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    // Quando autenticação for bem-sucedida, notifica SessionViewModel
    LaunchedEffect(authUiState.success) {
        if (authUiState.success && authUiState.accessToken != null) {
            sessionViewModel.login(authUiState.accessToken!!)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Conteúdo principal
        AuthScreenContent(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            isLoading = authUiState.loading,
            onSignIn = authViewModel::signin,
            onSignUp = authViewModel::signup
        )

        // Toast de erro (aparece por cima)
        ToastMessage(
            toastData = authUiState.toast,
            onDismiss = authViewModel::dismissToast
        )
    }
}


@Composable
private fun AuthScreenContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    isLoading: Boolean,
    onSignIn: (email: String, password: String) -> Unit,
    onSignUp: (name: String, email: String, password: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Header
        HeaderSection()

        Spacer(modifier = Modifier.height(32.dp))

        // Form Container
        Container {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Tab Selector
                AuthTabSelector(
                    selectedIndex = selectedTab,
                    onTabSelected = onTabSelected
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Forms
                when (selectedTab) {
                    0 -> SignInForm(
                        isLoading = isLoading,
                        onSignIn = onSignIn
                    )
                    1 -> SignUpForm(
                        isLoading = isLoading,
                        onSignUp = onSignUp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Footer
        FooterSection()
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    RabiscoTheme {
        AuthScreenContent(
            selectedTab = 0,
            onTabSelected = {},
            isLoading = false,
            onSignIn = { _, _ -> },
            onSignUp = { _, _, _ -> }
        )
    }
}