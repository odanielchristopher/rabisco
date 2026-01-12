package com.example.rabisco.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.remote.dto.auth.request.SignInDto
import com.example.rabisco.data.remote.dto.auth.request.SignUpDto
import com.example.rabisco.domain.repositories.AuthRepository
import com.example.rabisco.domain.utils.HttpErrorHandler
import com.example.rabisco.ui.components.ToastData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import retrofit2.HttpException
import kotlin.math.log


class AuthViewModel(
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

    private fun showToast(toast: ToastData) {
        _uiState.value = _uiState.value.copy(toast = toast)
    }

    fun dismissToast() {
        _uiState.value = _uiState.value.copy(toast = null)
    }

    private fun setAccessToken(accessToken: String?) {
        _uiState.value = _uiState.value.copy(accessToken = accessToken)
    }

    fun signin(email: String, password: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                setError(null)
                setSuccess(false)
                setAccessToken(null)

                authRepository.signin(SignInDto(email, password)).onSuccess {
                    setAccessToken(it.accessToken)
                    setSuccess(true)
                }.onFailure { error ->
                    handleError(error)
                }
            } catch (e: Exception) {
                handleError(e)
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
                setSuccess(false)
                setAccessToken(null)

                authRepository.signup(SignUpDto(name, email, password)).onSuccess {
                    setAccessToken(it.accessToken)
                    setSuccess(true)
                }.onFailure { error ->
                    handleError(error)
                }

            } catch (e: Exception) {
                handleError(e)
            } finally {
                setLoading(false)
            }
        }
    }

    private fun handleError(error: Throwable) {
        println(error.message)
        val toast = when (error) {
            is HttpException -> {
                HttpErrorHandler.handleHttpError(error.hashCode())
            }
            is IOException -> {
                HttpErrorHandler.handleNetworkError()
            }
            else -> {
                HttpErrorHandler.handleUnknownError(error)
            }
        }
        showToast(toast)
    }
}