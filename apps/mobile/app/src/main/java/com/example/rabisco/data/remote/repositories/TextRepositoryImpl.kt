package com.example.rabisco.data.remote.repositories

import com.example.rabisco.domain.models.Text
import com.example.rabisco.domain.repositories.TextRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class TextRepositoryImpl : TextRepository {
    // StateFlow para observar mudanças nos textos
    private val _textsFlow = MutableStateFlow<List<Text>>(emptyList())

    override val textsFlow: StateFlow<List<Text>> = _textsFlow.asStateFlow()

    override suspend fun saveText(text: Text): Result<String> {
        return try {
            delay(500) // Simula operação de rede/database
            val currentTexts = _textsFlow.value.toMutableList()
            val existingIndex = currentTexts.indexOfFirst { it.id == text.id }

            if (existingIndex != -1) {
                // Atualiza texto existente
                currentTexts[existingIndex] = text.copy(updatedAt = Date())
            } else {
                // Adiciona novo texto no início
                currentTexts.add(0, text)
            }

            _textsFlow.value = currentTexts
            Result.success(text.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTextById(id: String): Result<Text?> {
        return try {
            delay(100)
            val text = _textsFlow.value.find { it.id == id }
            Result.success(text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllTexts(): Result<List<Text>> {
        return try {
            delay(100)
            Result.success(_textsFlow.value)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteText(id: String): Result<Unit> {
        return try {
            delay(300)
            val currentTexts = _textsFlow.value.toMutableList()
            currentTexts.removeIf { it.id == id }
            _textsFlow.value = currentTexts
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTextsByTag(tag: String): Result<List<Text>> {
        return try {
            delay(100)
            val filtered = _textsFlow.value.filter {
                it.tags.contains(tag)
            }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}