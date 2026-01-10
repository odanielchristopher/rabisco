package com.example.rabisco.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updateAt")
    val updateAt: String
)