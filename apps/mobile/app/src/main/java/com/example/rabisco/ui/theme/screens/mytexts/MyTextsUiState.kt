package com.example.rabisco.ui.theme.screens.mytexts

import com.example.rabisco.data.Text

data class MyTextsUiState(
    val texts: List<Text> = emptyList(),
    val filteredTexts: List<Text> = emptyList(),
    val selectedTab: String = "Todos",
    val searchQuery: String = ""
)