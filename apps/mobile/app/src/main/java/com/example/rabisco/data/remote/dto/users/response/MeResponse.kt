package com.example.rabisco.data.remote.dto.users.response

data class MeResponse(
    val name: String,
    val email: String,
    val totalDays: Int,
    val totalTexts: Int,
    val textsThisWeek: Int,
    val textsToday: Int,
    val totalWord: Int,
    val score: Int,
)