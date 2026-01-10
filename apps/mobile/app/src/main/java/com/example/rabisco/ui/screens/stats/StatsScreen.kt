package com.example.rabisco.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.theme.RabiscoTheme

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Seu Progresso",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = "Acompanhe suas conquistas",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 40.dp, bottom = 24.dp)
        )

        // cards de stats
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    icon = Icons.Default.Description,
                    value = "0",
                    label = "textos",
                    color = Color(0xFF9C27B0),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Icons.Default.EmojiEvents,
                    value = "0",
                    label = "XP total",
                    color = Color(0xFFFFC107),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    icon = Icons.Default.Bolt,
                    value = "+0",
                    label = "XP hoje",
                    color = Color(0xFF00C853),
                    modifier = Modifier.weight(1f),
                    showGoal = true,
                    goalText = "Meta: 150 XP"
                )
                StatCard(
                    icon = Icons.Default.LocalFireDepartment,
                    value = "0",
                    label = "ofensiva",
                    color = Color(0xFFFF5722),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // placeholder pra conquistas
        Text(
            text = "Conquistas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Testeeeeeeeeeeeeeeeeeee",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier,
    showGoal: Boolean = false,
    goalText: String = ""
) {
    Card(
        modifier = modifier.height(135.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                if (showGoal) {
                    Text(
                        text = goalText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsPreview() {
    RabiscoTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            StatsScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsDarkPreview() {
    RabiscoTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            StatsScreen()
        }
    }
}
