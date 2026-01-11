package com.example.rabisco.domain.utils

fun isValidPassword(password: String): Boolean {
    return password.length >= 6
}