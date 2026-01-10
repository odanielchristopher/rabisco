package com.example.rabisco.data.remote.dto.request

data class UpdateTextDto(
    val type: String,
    val title: String,
    val content: String,
    val dailyPromptId: String? = null,
    val categoryIds: List<String>? = null,
    val tagIds: List<String>? = null
)