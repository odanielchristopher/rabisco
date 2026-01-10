package com.example.rabisco.data.remote.dto.request

data class UpdateUserDto(
    val name: String,
    val email: String,
    val currentPassword: String,
    val newPassword: String
)