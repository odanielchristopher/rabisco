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

    fun signin(email: String, password: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)

                authRepo.signin(SignInDto(email, password))

                setSuccess(true)
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)

                authRepo.signup(SignUpDto(name, email, password))

                setSuccess(true)
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
}
