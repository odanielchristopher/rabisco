package com.example.rabisco.data.remote.dto.users.response

import com.google.gson.annotations.SerializedName

data class MeResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("totalDays")
    val totalDays: Int,

    @SerializedName("totalTexts")
    val totalTexts: Int,

    @SerializedName("textsThisWeek")
    val textsThisWeek: Int,

    @SerializedName("textsToday")
    val textsToday: Int,

    @SerializedName("totalWord")
    val totalWord: Int,

    @SerializedName("score")
    val score: Int,
)