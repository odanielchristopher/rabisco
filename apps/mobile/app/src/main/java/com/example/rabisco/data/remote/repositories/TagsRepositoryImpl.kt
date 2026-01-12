package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.services.TagsService
import com.example.rabisco.domain.models.Tag
import com.example.rabisco.domain.repositories.TagsRepository

class TagsRepositoryImpl(
    private val tagsService: TagsService
) : TagsRepository {

    override suspend fun getAllTags(): Result<List<Tag>> {
        return try {
            val response = tagsService.getAll()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar tags: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}