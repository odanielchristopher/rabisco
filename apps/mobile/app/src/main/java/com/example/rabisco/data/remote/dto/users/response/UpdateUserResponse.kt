package com.example.rabisco.data.remote.dto.users.response

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,
)
