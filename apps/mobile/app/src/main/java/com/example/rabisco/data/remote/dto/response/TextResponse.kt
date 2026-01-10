package com.example.rabisco.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TextResponse (
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("wordCount")
    val wordCount: Int,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updateAt: String,

    @SerializedName("DailyPrompt")
    val dailyPrompt: String? = null
)