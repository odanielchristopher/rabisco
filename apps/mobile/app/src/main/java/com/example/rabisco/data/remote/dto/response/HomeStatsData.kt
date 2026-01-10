package com.example.rabisco.data.remote.dto.response

data class HomeStatsData (
    val streak: Int,
    val totalXP: Int,
    val totalTexts: Int,
    val textsWeek: Int,
    val promptOfDay: String
)