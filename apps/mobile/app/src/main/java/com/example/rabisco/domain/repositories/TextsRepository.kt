package com.example.rabisco.domain.repositories

import com.example.rabisco.data.remote.dto.texts.request.CreateTextDto
import com.example.rabisco.data.remote.dto.texts.request.UpdateTextDto
import com.example.rabisco.domain.models.Text

interface TextsRepository {
    suspend fun getAllTexts(category: String? = null, search: String? = null): Result<List<Text>>
    suspend fun getTextById(textId: String): Result<Text>
    suspend fun createText(createTextDto: CreateTextDto): Result<Text>
    suspend fun updateText(textId: String, updateTextDto: UpdateTextDto): Result<Text>
    suspend fun deleteText(textId: String): Result<Unit>
}