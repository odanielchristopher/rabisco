package com.example.rabisco.domain.repositories

import com.example.rabisco.domain.models.Tag

interface TagsRepository {
    suspend fun getAllTags(): Result<List<Tag>>
}