package com.example.rabisco.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class TagResponse (
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)