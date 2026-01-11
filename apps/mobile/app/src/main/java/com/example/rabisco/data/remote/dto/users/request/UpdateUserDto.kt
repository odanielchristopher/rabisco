package com.example.rabisco.data.remote.dto.users.request

data class UpdateUserDto(
    val name: String,
    val email: String,
    val currentPassword: String,
    val newPassword: String,
)