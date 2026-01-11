package com.example.rabisco.data.remote.dto.texts

import com.example.rabisco.data.remote.dto.texts.request.CreateTextDto
import com.example.rabisco.data.remote.dto.texts.request.UpdateTextDto
import com.example.rabisco.domain.models.Text
import com.example.rabisco.domain.models.TextType

fun Text.toCreateDto(): CreateTextDto {
    return CreateTextDto(
        title = title,
        content = content,
        type = TextType.FREE,
        categoryIds = null,
        dailyPromptId = null,
        tagsIds = null
    )
}

fun Text.toUpdateDto(): UpdateTextDto {
    return UpdateTextDto(
        title = title,
        content = content,
        type = TextType.FREE,
        categoryIds = null,
        dailyPromptId = null,
        tagsIds = null
    )
}