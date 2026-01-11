package com.example.rabisco.ui.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.components.AppButton
import com.example.rabisco.ui.components.AppInput
import com.example.rabisco.ui.screens.auth.models.SignInFormState
import com.example.rabisco.ui.screens.auth.validation.AuthValidation

@Composable
fun SignInForm(
    isLoading: Boolean,
    errorMessage: String?,
    onSignIn: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var formState by remember { mutableStateOf(SignInFormState()) }

    fun handleSubmit() {
        val validatedState = AuthValidation.validateSignIn(formState)
        formState = validatedState

        if (validatedState.isValid) {
            onSignIn(validatedState.email, validatedState.password)
        }
    }

    Column(modifier = modifier) {
        // Email Field
        AppInput(
            label = "E-mail",
            value = formState.email,
            onChange = {
                formState = formState.copy(
                    email = it,
                    emailError = null // Limpa erro ao digitar
                )
            },
            error = formState.emailError
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        AppInput(
            label = "Senha",
            value = formState.password,
            onChange = {
                formState = formState.copy(
                    password = it,
                    passwordError = null
                )
            },
            error = formState.passwordError
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        AppButton(
            text = "Entrar",
            onClick = ::handleSubmit,
            loading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        // API Error Message
        errorMessage?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}