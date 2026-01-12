package com.example.rabisco.ui.screens.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rabisco.data.remote.dto.texts.request.CreateTextDto
import com.example.rabisco.data.remote.dto.texts.request.UpdateTextDto
import com.example.rabisco.domain.models.Text
import com.example.rabisco.domain.models.TextType
import com.example.rabisco.domain.repositories.TextsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class WriteViewModel(
    private val textsRepository: TextsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WriteUiState())
    val uiState: StateFlow<WriteUiState> = _uiState.asStateFlow()

    private var textIdToEdit: String? = null

    fun loadTextForEdit(textId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            textsRepository.getTextById(textId)
                .onSuccess { text ->
                    textIdToEdit = textId
                    _uiState.update {
                        it.copy(
                            title = text.title,
                            content = text.content,
                            selectedTags = text.tags?.toSet() ?: emptySet(),
                            wordCount = text.wordCount,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Erro carregando texto: ${exception.message}"
                        )
                    }
                }
        }
    }

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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val currentState = _uiState.value

                if(currentState.content.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "O conteúdo não pode estar vazio"
                        )
                    }
                    return@launch
                }

                if(textIdToEdit != null) {
                    updateExistingText(textIdToEdit!!, currentState)
                } else {
                    createNewText(currentState)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro inesperado: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun createNewText(currentState: WriteUiState) {
        val createDto = CreateTextDto(
            title = currentState.title.ifBlank { "Sem título" },
            content = currentState.content,
            type = TextType.FREE,
            categoryIds = null,
            dailyPromptId = null,
            tagsIds = null
        )

        textsRepository.createText(createDto)
            .onSuccess { text ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        textSaved = true
                    )
                }
                println("texto criado :) parabens ${text.id}")
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro ao salvar :( ${exception.message}"
                    )
                }
            }
    }

    private suspend fun updateExistingText(textId: String, currentState: WriteUiState) {
        val updateDto = UpdateTextDto(
            title = currentState.title.ifBlank { "Sem título" },
            content = currentState.content,
            type = TextType.FREE,
            categoryIds = null,
            dailyPromptId = null,
            tagsIds = null
        )

        textsRepository.updateText(textId, updateDto)
            .onSuccess { text ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        textSaved = true
                    )
                }
                println("texto atualizado :) ${text.id}")
            }
            .onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "erro ao atualizar :( ${exception.message}"
                    )
                }
            }
    }

    private fun calculateWordCount(text: String): Int {
        if (text.isBlank()) return 0
        return text.trim().split("\\s+".toRegex()).size
    }
}