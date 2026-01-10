package com.example.rabisco.domain.repositories

import com.example.rabisco.domain.models.Text
import kotlinx.coroutines.flow.StateFlow

interface TextRepository {
    val textsFlow: StateFlow<List<Text>>
    suspend fun saveText(text: Text): Result<String>
    suspend fun getTextById(id: String): Result<Text?>
    suspend fun getAllTexts(): Result<List<Text>>
    suspend fun deleteText(id: String): Result<Unit>
    suspend fun getTextsByTag(tag: String): Result<List<Text>>
}