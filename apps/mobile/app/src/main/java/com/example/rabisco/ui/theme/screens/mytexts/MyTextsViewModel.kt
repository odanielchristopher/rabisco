package com.example.rabisco.ui.theme.screens.mytexts

import androidx.lifecycle.ViewModel
import com.example.rabisco.data.Text
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class MyTextsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyTextsUiState())
    val uiState: StateFlow<MyTextsUiState> = _uiState.asStateFlow()

    init {
        val mockTexts = listOf(
            Text(1, "Teste", "fdsfasdfasf", Date(), 1, "Pessoal"),
            Text(2, "Outro Teste", "mais um teste", Date(), 3, "Escola")
        )
        _uiState.value = _uiState.value.copy(
            texts = mockTexts,
            filteredTexts = mockTexts
        )
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onTabSelected(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}