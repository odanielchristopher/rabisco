package com.example.rabisco.domain.utils

import com.example.rabisco.ui.components.ToastData
import com.example.rabisco.ui.components.ToastType

object HttpErrorHandler {

    fun handleHttpError(statusCode: Int): ToastData {
        return when (statusCode) {
            400 -> ToastData(
                message = "Dados inválidos. Verifique as informações.",
                type = ToastType.WARNING
            )
            401 -> ToastData(
                message = "Credenciais inválidas. Tente novamente.",
                type = ToastType.ERROR
            )
            403 -> ToastData(
                message = "Acesso negado.",
                type = ToastType.ERROR
            )
            404 -> ToastData(
                message = "Recurso não encontrado.",
                type = ToastType.WARNING
            )
            409 -> ToastData(
                message = "Este e-mail já está cadastrado.",
                type = ToastType.WARNING
            )
            422 -> ToastData(
                message = "Dados inválidos. Verifique os campos.",
                type = ToastType.WARNING
            )
            500 -> ToastData(
                message = "Erro no servidor. Tente novamente mais tarde.",
                type = ToastType.ERROR
            )
            503 -> ToastData(
                message = "Serviço temporariamente indisponível.",
                type = ToastType.WARNING
            )
            else -> ToastData(
                message = "Erro desconhecido. Código: $statusCode",
                type = ToastType.ERROR
            )
        }
    }

    fun handleNetworkError(): ToastData {
        return ToastData(
            message = "Erro de conexão. Verifique sua internet.",
            type = ToastType.ERROR
        )
    }

    fun handleUnknownError(error: Throwable?): ToastData {
        return ToastData(
            message = error?.message ?: "Erro inesperado. Tente novamente.",
            type = ToastType.ERROR
        )
    }
}