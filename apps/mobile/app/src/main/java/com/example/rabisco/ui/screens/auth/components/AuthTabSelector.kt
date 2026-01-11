package com.example.rabisco.ui.screens.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTabSelector(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabs: List<String> = listOf("Entrar", "Cadastrar"),
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        tabs.forEachIndexed { index, label ->
            SegmentedButton(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                shape = SegmentedButtonDefaults.itemShape(index, tabs.size),
                icon = {},
                label = { Text(label) },
                modifier = Modifier.border(0.dp, color = Color.Transparent),
                border = BorderStroke(0.dp, Color.Transparent)
            )
        }
    }
}