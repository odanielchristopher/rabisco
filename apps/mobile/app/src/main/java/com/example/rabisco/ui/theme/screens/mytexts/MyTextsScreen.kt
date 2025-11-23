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

            Text("List coming here", modifier = Modifier.padding(16.dp))
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

@Preview(showBackground = true)
@Composable
fun MyTextsScreenPreview() {
    MaterialTheme {
        MyTextsScreen(onNavigateToWrite = {})
    }
}