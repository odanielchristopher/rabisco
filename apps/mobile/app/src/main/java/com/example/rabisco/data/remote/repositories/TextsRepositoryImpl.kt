package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.dto.texts.request.CreateTextDto
import com.example.rabisco.data.remote.dto.texts.request.UpdateTextDto
import com.example.rabisco.data.remote.services.TextsService
import com.example.rabisco.domain.models.Text
import com.example.rabisco.domain.repositories.TextsRepository

class TextsRepositoryImpl(
    private val textsService: TextsService
) : TextsRepository {

    override suspend fun getAllTexts(category: String?, search: String?): Result<List<Text>> {
        return try {
            val response = textsService.getAll(category, search)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar textos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTextById(textId: String): Result<Text> {
        return try {
            val response = textsService.getOne(textId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar texto: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createText(createTextDto: CreateTextDto): Result<Text> {
        return try {
            val response = textsService.create(createTextDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao criar texto: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateText(textId: String, updateTextDto: UpdateTextDto): Result<Text> {
        return try {
            val response = textsService.update(textId, updateTextDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao atualizar texto: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteText(textId: String): Result<Unit> {
        return try {
            textsService.remove(textId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}