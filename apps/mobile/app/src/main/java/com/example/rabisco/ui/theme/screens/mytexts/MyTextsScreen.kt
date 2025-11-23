package com.example.rabisco.ui.theme.screens.mytexts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import com.example.rabisco.data.Text
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun MyTextsScreen(
    onNavigateToWrite: () -> Unit,
    viewModel: MyTextsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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

            TextsList(
                texts = uiState.filteredTexts,
                onDeleteClick = { /* TODO */ }
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
fun TextsList(texts: List<Text>, onDeleteClick: (Text) -> Unit) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(texts) { text ->
            TextCard(text, onDeleteClick)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TextCard(text: Text, onDeleteClick: (Text) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text.title, style = MaterialTheme.typography.titleMedium)

            Text(
                text.content,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                val formattedDate = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(text.date)

                Text(
                    "$formattedDate • ${text.wordCount} palavras",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { /* TODO */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }

                IconButton(onClick = { onDeleteClick(text) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTextsScreenPreview() {
    MaterialTheme {
        MyTextsScreen(onNavigateToWrite = {})
    }
}