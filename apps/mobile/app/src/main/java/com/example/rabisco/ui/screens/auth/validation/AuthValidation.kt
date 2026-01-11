package com.example.rabisco.ui.screens.auth.validation

import com.example.rabisco.domain.utils.isValidEmail
import com.example.rabisco.domain.utils.isValidPassword
import com.example.rabisco.ui.screens.auth.models.SignInFormState
import com.example.rabisco.ui.screens.auth.models.SignUpFormState

object AuthValidation {

    /**
     * Valida o formulário de Sign In
     * @return SignInFormState com erros preenchidos
     */
    fun validateSignIn(state: SignInFormState): SignInFormState {
        val emailError = when {
            state.email.isBlank() -> "E-mail é obrigatório"
            !isValidEmail(state.email) -> "Precisa ser um e-mail válido"
            else -> null
        }

        val passwordError = when {
            state.password.isBlank() -> "Senha é obrigatória"
            !isValidPassword(state.password) -> "Senha deve ter pelo menos 6 caracteres"
            else -> null
        }

        return state.copy(
            emailError = emailError,
            passwordError = passwordError
        )
    }

    /**
     * Valida o formulário de Sign Up
     * @return SignUpFormState com erros preenchidos
     */
    fun validateSignUp(state: SignUpFormState): SignUpFormState {
        val nameError = when {
            state.name.isBlank() -> "Nome é obrigatório"
            state.name.length < 2 -> "Nome deve ter pelo menos 2 caracteres"
            else -> null
        }

        val emailError = when {
            state.email.isBlank() -> "E-mail é obrigatório"
            !isValidEmail(state.email) -> "E-mail inválido"
            else -> null
        }

        val passwordError = when {
            state.password.isBlank() -> "Senha é obrigatória"
            !isValidPassword(state.password) -> "Senha deve ter pelo menos 6 caracteres"
            else -> null
        }

        val confirmPasswordError = when {
            state.confirmPassword.isBlank() -> "Confirme sua senha"
            state.confirmPassword != state.password -> "As senhas não coincidem"
            else -> null
        }

        return state.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )
    }
}