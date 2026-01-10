package com.example.rabisco.ui.screens.mytexts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rabisco.domain.models.Text
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.rabisco.domain.repositories.TextRepository
import kotlinx.coroutines.launch

class MyTextsViewModel(private val textRepository: TextRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MyTextsUiState())
    val uiState: StateFlow<MyTextsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            textRepository.textsFlow.collect { texts ->
                _uiState.update { it.copy(texts = texts) }
                filterTexts()
            }
        }
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
            val matchesCategory = state.selectedTab == "Todos" || text.tags.contains(state.selectedTab)
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
                textRepository.deleteText(text.id)
                _uiState.update {
                    it.copy(
                        showDeleteConfirmation = false,
                        textToDelete = null
                    )
                }
                filterTexts()
            }
        }
    }
}