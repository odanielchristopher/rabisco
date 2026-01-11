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
import com.example.rabisco.domain.repositories.TextsRepository
import kotlinx.coroutines.launch

class MyTextsViewModel(private val textsRepository: TextsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MyTextsUiState())
    val uiState: StateFlow<MyTextsUiState> = _uiState.asStateFlow()

    init {
        loadTexts()
    }

    private fun loadTexts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            textsRepository.getAllTexts(
                category = null,
                search = null
            ).onSuccess { texts ->
                _uiState.update {
                    it.copy(
                        texts = texts,
                        isLoading = false
                    )
                }
                filterTexts()
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "erro no carregamento ${exception.message}"
                    )
                }
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
                textsRepository.deleteText(text.id)
                    .onSuccess {
                        loadTexts()
                        _uiState.update {
                            it.copy(
                                showDeleteConfirmation = false,
                                textToDelete = null,
                                isLoading = false
                            )
                        }
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "erro ao deletar: ${exception.message}",
                                showDeleteConfirmation = false,
                                textToDelete = null
                            )
                        }
                    }
            }
        }
    }

    fun refresh() {
        loadTexts()
    }
}