package com.example.rabisco.ui.screens.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rabisco.ui.theme.RabiscoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    onNavigateBack: () -> Unit = {},
    onTextSaved: () -> Unit = {},
    viewModel: WriteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.textSaved) {
        if(uiState.textSaved) {
            onTextSaved()
        }
    }

    Scaffold(
        topBar = {
            WriteTopBar(
                wordCount = uiState.wordCount,
                onNavigateBack = onNavigateBack,
                onSave = { viewModel.saveText() }
            )
        }
    ) {
        paddingValues ->
        WriteContent (
            modifier = Modifier.padding(paddingValues),
            title = uiState.title,
            content = uiState.content,
            selectedTags = uiState.selectedTags,
            onTitleChange = { viewModel.updateTitle(it) },
            onContentChange = { viewModel.updateContent(it) },
            onTagClick = { viewModel.toggleTag(it) },
            onSave = { viewModel.saveText() },
            isLoading = uiState.isLoading,
            errorMessage = uiState.errorMessage
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTopBar(
    wordCount: Int,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text (
                    text = "$wordCount palavras",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun WriteContent (
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    selectedTags: Set<String>,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onSave: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text (
                    text = "Título do texto",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            textStyle = MaterialTheme.typography.titleMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            ),
        )

        Spacer( modifier = Modifier.height(8.dp) )

        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text (
                    text = "Sobre o que quer escrever?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            ),
            minLines = 15,
            maxLines = 20
        )

        Spacer(modifier = Modifier.height(24.dp))

        CategorieSection(
            selectedTags = selectedTags,
            onTagClick = onTagClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        errorMessage?.let {error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if(isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Salvar")
            }
        }
    }
}

@Composable
private fun CategorieSection(
    selectedTags: Set<String>,
    onTagClick: (String) -> Unit
) {
    val tags = listOf(
        "Pessoal", "Escola", "Família", "Amigos", "Sonhos", "Reflexões", "Gratidão", "Objetivos", "Criatividade", "Aventuras"
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text (
                text = "Categorias",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            //var currentRow by remember { mutableStateOf(listOf<String>()) }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.chunked(3).forEach { rowTags ->
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowTags.forEach { tag ->
                            TagChip (
                                text = tag,
                                isSelected = selectedTags.contains(tag),
                                onClick = { onTagClick(tag) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        repeat( 3 - rowTags.size ) {
                            Spacer(modifier  = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text (
                text = text,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = modifier,
         colors = FilterChipDefaults.filterChipColors(
             selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
             selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
         )
    )
}


@Preview(showBackground = true)
@Composable
fun WriteScreenPreview() {
    RabiscoTheme {
        WriteScreen()
    }
}