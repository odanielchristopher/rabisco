package com.example.rabisco.domain.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class Text(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String,
    val tags: List<String> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val wordCount: Int = 0
) {
    fun isEmpty(): Boolean = content.isBlank()

    fun getPreview(): String {
        return if (content.length > 100) {
            content.take(97) + "..."
        } else {
            content
        }
    }

    fun getFormattedDate(): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(createdAt)
    }

    fun getPrimaryTag(): String {
        return tags.firstOrNull() ?: "Sem categorais"
    }
}

enum class TextType {
    DIARY,
    DAY_PROMPT,
    FREE
}