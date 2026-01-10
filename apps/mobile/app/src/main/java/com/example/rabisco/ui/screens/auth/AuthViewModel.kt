package com.example.rabisco.ui.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.request.SignInDto
import com.example.rabisco.data.remote.dto.request.SignUpDto
import com.example.rabisco.data.remote.repositories.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(context: Context) : ViewModel() {

    private val sessionRepo = SessionRepository(context)
    private val authRepo = AuthRepository(sessionRepo)

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private fun setLoading(value: Boolean) {
        _uiState.value = _uiState.value.copy(loading = value)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(error = message)
    }

    private fun setSuccess(value: Boolean) {
        _uiState.value = _uiState.value.copy(success = value)
    }

    /**
     * CORRIGIDO: Agora trata Result<T> corretamente
     * Valida campos antes de enviar
     */
    fun signin(email: String, password: String) {
        // Validação de campos vazios
        if (email.isBlank() || password.isBlank()) {
            setError("Preencha todos os campos")
            return
        }

        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)

                // CORREÇÃO: Verifica o Result
                authRepo.signin(SignInDto(email, password))
                    .onSuccess { response ->
                        println("✅ Login bem-sucedido! Token: ${response.accessToken.take(20)}...")
                        setSuccess(true)
                    }
                    .onFailure { exception ->
                        println("❌ Erro no login: ${exception.message}")
                        setError(exception.message ?: "Erro ao fazer login")
                        setSuccess(false)
                    }

            } catch (e: Exception) {
                println("❌ Exceção no signin: ${e.message}")
                setError(e.message ?: "Erro inesperado")
                setSuccess(false)
            } finally {
                setLoading(false)
            }
        }
    }

    /**
     * CORRIGIDO: Agora trata Result<T> corretamente
     * Valida campos antes de enviar
     */
    fun signup(name: String, email: String, password: String) {
        // Validação de campos vazios
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            setError("Preencha todos os campos")
            return
        }

        // Validação de senha mínima
        if (password.length < 6) {
            setError("A senha deve ter pelo menos 6 caracteres")
            return
        }

        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)

                // CORREÇÃO: Verifica o Result
                authRepo.signup(SignUpDto(name, email, password))
                    .onSuccess { response ->
                        println("✅ Cadastro bem-sucedido! Token: ${response.accessToken.take(20)}...")
                        setSuccess(true)
                    }
                    .onFailure { exception ->
                        println("❌ Erro no cadastro: ${exception.message}")
                        setError(exception.message ?: "Erro ao fazer cadastro")
                        setSuccess(false)
                    }

            } catch (e: Exception) {
                println("❌ Exceção no signup: ${e.message}")
                setError(e.message ?: "Erro inesperado")
                setSuccess(false)
            } finally {
                setLoading(false)
            }
        }
    }

    /**
     * Reseta o estado de sucesso (para evitar navegação múltipla)
     */
    fun resetSuccess() {
        setSuccess(false)
    }
}