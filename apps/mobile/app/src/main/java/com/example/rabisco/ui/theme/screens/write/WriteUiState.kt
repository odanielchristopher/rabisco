package com.example.rabisco.ui.theme.screens.write

data class WriteUiState(
    val title: String = "",
    val content: String = "",
    val selectedTags: Set<String> = emptySet(),
    val wordCount: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val textSaved: Boolean = false
)