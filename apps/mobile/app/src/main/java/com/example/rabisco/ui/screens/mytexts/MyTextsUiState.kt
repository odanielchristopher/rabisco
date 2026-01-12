package com.example.rabisco.ui.screens.mytexts

import com.example.rabisco.domain.models.Text

data class MyTextsUiState(
    val texts: List<Text> = emptyList(),
    val filteredTexts: List<Text> = emptyList(),
    val selectedTab: String = "Todos",
    val searchQuery: String = "",
    val showDeleteConfirmation: Boolean = false,
    val textToDelete: Text? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)