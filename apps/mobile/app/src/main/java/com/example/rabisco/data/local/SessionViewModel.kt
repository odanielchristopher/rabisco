package com.example.rabisco.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkSession()
        observeToken()
    }

    private fun observeToken() {
        viewModelScope.launch {
            sessionRepository.observeToken().collect { token ->
                _isLoggedIn.value = !token.isNullOrBlank()
            }
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            val token = sessionRepository.getToken()
            _isLoggedIn.value = !token.isNullOrBlank()
        }
    }

    fun login(accessToken: String) {
        viewModelScope.launch {
            sessionRepository.saveToken(accessToken)
            _isLoggedIn.value = true
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionRepository.clearToken()
            _isLoggedIn.value = false
        }
    }
}
