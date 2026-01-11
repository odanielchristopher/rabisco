package com.example.rabisco.ui.screens.mytexts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.domain.models.Text
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyTextsScreen(
    onNavigateToWrite: () -> Unit,
    onNavigateToEdit: (String) -> Unit = {},
    viewModel: MyTextsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        topBar = { MyTextsTopBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChanged = viewModel::onSearchQueryChanged
            )

            CategoryTabs(
                selectedTab = uiState.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.errorMessage != null) {
                ErrorState(
                    message = uiState.errorMessage!!,
                    onRetry = { viewModel.refresh() }
                )
            } else if (uiState.filteredTexts.isEmpty()) {
                EmptyState()
            } else {
                TextsList(
                    texts = uiState.filteredTexts,
                    onDeleteClick = viewModel::onDeleteConfirmation,
                    onEditClick = onNavigateToEdit
                )
            }
        }

        if (uiState.showDeleteConfirmation) {
            DeleteConfirmationDialog(
                onConfirm = viewModel::deleteText,
                onDismiss = viewModel::onDismissDeleteConfirmation
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextsTopBar() {
    TopAppBar(
        title = { Text("Meus Textos") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Buscar nos seus textos...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true
    )
}

@Composable
fun CategoryTabs(selectedTab: String, onTabSelected: (String) -> Unit) {
    val categories = listOf("Todos", "Pessoal", "Escola", "Família", "Amigos", "Sonhos", "Reflexão")

    ScrollableTabRow(
        selectedTabIndex = categories.indexOf(selectedTab),
        modifier = Modifier.fillMaxWidth(),
        edgePadding = 16.dp
    ) {
        categories.forEach { category ->
            Tab(
                selected = category == selectedTab,
                onClick = { onTabSelected(category) },
                text = { Text(category) }
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Edit,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nenhum texto ainda", style = MaterialTheme.typography.titleMedium)
        Text(
            "Comece a escrever para ver seus textos aqui",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Erro ao carregar textos",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Tentar novamente")
        }
    }
}

@Composable
fun TextsList(texts: List<Text>, onDeleteClick: (Text) -> Unit, onEditClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(texts) { text ->
            TextCard(text, onDeleteClick, onEditClick = onEditClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TextCard(text: Text, onDeleteClick: (Text) -> Unit, onEditClick: (String) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text.title, style = MaterialTheme.typography.titleMedium)

            Text(
                text.getPreview(),
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if(!text.tags.isNullOrEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    text.tags.take(3).forEach { tag ->
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    tag,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${text.getFormattedDate()} • ${text.wordCount} palavras",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { onEditClick(text.id) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }

                IconButton(onClick = { onDeleteClick(text) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Excluir texto?") },
        text = {
            Text("Esta ação não pode ser desfeita. O texto será permanentemente excluído.")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Excluir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyTextsScreenPreview() {
    MaterialTheme() {
        MyTextsScreen(onNavigateToWrite = {})
    }
}