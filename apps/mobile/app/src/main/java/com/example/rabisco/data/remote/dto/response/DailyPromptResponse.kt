package com.example.rabisco.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class DailyPromptResponse (
    @SerializedName("id")
    val id: String,

    @SerializedName("prompt")
    val prompt: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updateAt")
    val updatedAt: String
)