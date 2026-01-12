package com.example.rabisco.domain.repositories

import com.example.rabisco.data.remote.dto.users.request.UpdateUserDto
import com.example.rabisco.data.remote.dto.users.response.MeResponse
import com.example.rabisco.data.remote.dto.users.response.UpdateUserResponse

interface UserRepository {
    suspend fun getMe(): Result<MeResponse>
    suspend fun updateUser(updateUserDto: UpdateUserDto): Result<UpdateUserResponse>
    suspend fun deleteUser(): Result<Unit>
}