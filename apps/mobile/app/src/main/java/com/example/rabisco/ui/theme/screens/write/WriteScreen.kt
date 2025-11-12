package com.example.rabisco.ui.theme.screens.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.theme.RabiscoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: WriteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
            onTagClick = { viewModel.toggleTag(it) }
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text (
                    text = "$wordCount palavras",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        navigationIcon = {

        }
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
    onTagClick: (String) -> Unit
) {
    Column() { }
}


@Preview(showBackground = true)
@Composable
fun WriteScreenPreview() {
    RabiscoTheme {
        WriteScreen()
    }
}