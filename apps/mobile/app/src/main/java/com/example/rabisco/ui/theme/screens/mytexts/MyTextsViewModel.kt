package com.example.rabisco.ui.theme.screens.mytexts

import androidx.lifecycle.ViewModel
import com.example.rabisco.data.Text
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
        filterTexts()
    }

    fun onTabSelected(tab: String) {
        _uiState.update { it.copy(selectedTab = tab) }
        filterTexts()
    }

    private fun filterTexts() {
        val state = _uiState.value
        val filtered = state.texts.filter { text ->
            val matchesCategory = state.selectedTab == "Todos" || text.category == state.selectedTab
            val matchesSearch = state.searchQuery.isBlank() ||
                    text.title.contains(state.searchQuery, ignoreCase = true) ||
                    text.content.contains(state.searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
        _uiState.update { it.copy(filteredTexts = filtered) }
    }

    fun onDeleteConfirmation(text: Text) {
        _uiState.update { it.copy(showDeleteConfirmation = true, textToDelete = text) }
    }

    fun onDismissDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteConfirmation = false, textToDelete = null) }
    }
    fun deleteText() {
        viewModelScope.launch {
            _uiState.value.textToDelete?.let { text ->
                val updatedTexts = _uiState.value.texts.filter { it.id != text.id }
                _uiState.update {
                    it.copy(
                        texts = updatedTexts,
                        showDeleteConfirmation = false,
                        textToDelete = null
                    )
                }
                filterTexts()
            }
        }
    }
}