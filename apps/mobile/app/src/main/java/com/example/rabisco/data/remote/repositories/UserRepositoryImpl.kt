package com.example.rabisco.data.remote.repositories

import com.example.rabisco.data.remote.dto.users.request.UpdateUserDto
import com.example.rabisco.data.remote.dto.users.response.MeResponse
import com.example.rabisco.data.remote.dto.users.response.UpdateUserResponse
import com.example.rabisco.data.remote.services.UserService
import com.example.rabisco.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userService: UserService
) : UserRepository {

    override suspend fun getMe(): Result<MeResponse> {
        return try {
            val response = userService.me()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao buscar dados do usuário: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(updateUserDto: UpdateUserDto): Result<UpdateUserResponse> {
        return try {
            val response = userService.update(updateUserDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Erro ao atualizar usuário: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(): Result<Unit> {
        return try {
            userService.remove()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}