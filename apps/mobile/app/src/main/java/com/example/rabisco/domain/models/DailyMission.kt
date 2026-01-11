package com.example.rabisco.domain.models

import java.util.Date

data class DailyMission(
    val id: String,
    val description: String,
    val type: MissionType,
    val goal: Int,
    val progress: Int,
    val completed: Boolean,
    val completionDate: Date?,
    val points: Int,
)

enum class MissionType() {
    WRITE_TEXT,
    WORD_QUANTITY
}

