package com.example.rabisco.data.remote.dto.texts.request

import com.example.rabisco.domain.models.TextType

data class CreateTextDto(
    val title: String,
    val content: String,
    val type: TextType,
    val categoryIds: List<String>?,
    val dailyPromptId: String?,
    val tagsIds: List<String>?
)
