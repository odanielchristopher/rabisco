package com.example.rabisco.ui.screens.stats

import com.example.rabisco.domain.models.Achievement
import com.example.rabisco.domain.models.DailyMission
import com.example.rabisco.domain.models.MissionType
import java.util.*
import java.util.concurrent.TimeUnit

// ====================================================================
// Mapper: Domain Achievement → UI Achievement
// ====================================================================
fun Achievement.toUiModel(): UiAchievement {
    val progressPercent = if (goal > 0) progress.toFloat() / goal.toFloat() else 0f

    return UiAchievement(
        title = title,
        description = description,
        progress = progressPercent.coerceIn(0f, 1f),
        progressText = "$progress/$goal ${getProgressUnit(code)}",
        rewardText = "Recompensa: +${calculateReward(code)} XP",
        iconName = getIconName(code),
        isCompleted = achieved
    )
}

private fun getProgressUnit(code: String): String {
    return when {
        code.contains("text", ignoreCase = true) -> "textos"
        code.contains("word", ignoreCase = true) -> "palavras"
        code.contains("day", ignoreCase = true) || code.contains("streak", ignoreCase = true) -> "dias"
        else -> "progresso"
    }
}

private fun calculateReward(code: String): Int {
    return when {
        code.contains("first", ignoreCase = true) -> 10
        code.contains("100", ignoreCase = true) -> 50
        code.contains("streak", ignoreCase = true) -> 100
        code.contains("marathon", ignoreCase = true) -> 200
        else -> 25
    }
}

private fun getIconName(code: String): String {
    return when {
        code.contains("first", ignoreCase = true) -> "target"
        code.contains("word", ignoreCase = true) -> "description"
        code.contains("streak", ignoreCase = true) -> "fire"
        code.contains("marathon", ignoreCase = true) -> "trophy"
        else -> "star"
    }
}

// ====================================================================
// Mapper: Domain DailyMission → UI DailyMission
// ====================================================================
fun DailyMission.toUiModel(): UiDailyMission {
    val progressPercent = if (goal > 0) progress.toFloat() / goal.toFloat() else 0f

    return UiDailyMission(
        title = getTitleByType(type),
        description = description,
        progress = progressPercent.coerceIn(0f, 1f),
        progressText = "$progress/$goal ${getUnitByType(type)}",
        rewardText = "+$points XP",
        iconName = getIconByType(type),
        renovaEm = calculateRenewalTime(),
        isCompleted = completed
    )
}

private fun getTitleByType(type: MissionType): String {
    return when (type) {
        MissionType.WRITE_TEXT -> "Escreva textos"
        MissionType.WORD_QUANTITY -> "Alcance a meta de palavras"
    }
}

private fun getUnitByType(type: MissionType): String {
    return when (type) {
        MissionType.WRITE_TEXT -> "texto(s)"
        MissionType.WORD_QUANTITY -> "palavras"
    }
}

private fun getIconByType(type: MissionType): String {
    return when (type) {
        MissionType.WRITE_TEXT -> "edit"
        MissionType.WORD_QUANTITY -> "book"
    }
}

private fun calculateRenewalTime(): String {
    val now = Calendar.getInstance()
    val midnight = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 24)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val diff = midnight.timeInMillis - now.timeInMillis
    val hours = TimeUnit.MILLISECONDS.toHours(diff)

    return "${hours}h"
}