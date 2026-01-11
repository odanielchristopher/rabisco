package com.example.rabisco.data.remote.utils

import com.example.rabisco.data.remote.dto.ApiErrorDto
import com.google.gson.Gson

fun <T> retrofitError(response: retrofit2.Response<T>): Exception {
    val errorBody = response.errorBody()?.string()

    val message = try {
        Gson().fromJson(errorBody, ApiErrorDto::class.java).message
    } catch (e: Exception) {
        errorBody ?: "Erro desconhecido"
    }

    return Exception(message)
}
