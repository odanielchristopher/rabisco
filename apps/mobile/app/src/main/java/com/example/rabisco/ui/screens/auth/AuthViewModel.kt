package com.example.rabisco.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.local.SessionRepository
import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.domain.repositories.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class AuthViewModel(
    private val sessionRepository: SessionRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

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

                authRepository.signin(SignInDto(email, password)).onSuccess {
                    body -> sessionRepository.saveToken(body.accessToken)
                }

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

                authRepository.signup(SignUpDto(name, email, password)).onSuccess {
                    body -> sessionRepository.saveToken(body.accessToken)
                }

                setSuccess(true)
            } catch (e: Exception) {
                setError(e.message)
            } finally {
                setLoading(false)
            }
        }
    }
}