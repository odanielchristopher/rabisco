package com.example.rabisco.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.screens.auth.SignInScreen
import com.example.rabisco.ui.theme.RabiscoTheme


@Composable
fun Container(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier.background(
            color = scheme.surfaceContainer,
            shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = scheme.outlineVariant,
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = scheme.surface
        ),
        border = BorderStroke(1.dp, scheme.outlineVariant)
    ) {
        Column(content = content)
    }
}


//@Preview
//@Composable
//fun ContainerPreview() {
//    RabiscoTheme() {
//        Container {
//            SignInScreen()
//        }
//    }
//}