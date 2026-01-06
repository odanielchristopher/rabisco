package com.example.rabisco.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rabisco.ui.theme.RabiscoTheme

@Composable
fun HomeScreen( ) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->


    }
}

@Preview
@Composable
fun HomePreview() {
    RabiscoTheme {
        HomeScreen()
    }
}