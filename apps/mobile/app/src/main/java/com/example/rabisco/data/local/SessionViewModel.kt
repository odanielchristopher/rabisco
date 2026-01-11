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
        _isLoggedIn.value = false
    }
}
