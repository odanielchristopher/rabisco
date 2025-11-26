package com.example.rabisco.ui.theme.screens.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.contains


//essa data class vai para o uiState (estudar isso)

class WriteViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WriteUiState())
    val uiState: StateFlow<WriteUiState> = _uiState.asStateFlow()

    fun updateTitle(newTitle: String) {
        _uiState.update { currentState -> currentState.copy(title = newTitle) }
    }

    fun updateContent(newContent: String) {
        _uiState.update { currentState ->
            currentState.copy(
                content = newContent,
                wordCount = calculateWordCount(newContent)
            )
        }
    }

    fun toggleTag(tag: String) {
        _uiState.update { currentState ->
            val newTags = if(currentState.selectedTags.contains(tag)) {
                currentState.selectedTags - tag
            } else {
                currentState.selectedTags + tag
            }
            currentState.copy(selectedTags = newTags)
        }
    }

    fun saveText() {
        //como fazer?
    }

    private fun calculateWordCount(text: String): Int {
        if (text.isBlank()) return 0
        return text.trim().split("\\s+".toRegex()).size
    }
}

