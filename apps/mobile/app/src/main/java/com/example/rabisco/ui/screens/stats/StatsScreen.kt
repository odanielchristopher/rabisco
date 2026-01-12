package com.example.rabisco.ui.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.theme.RabiscoTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = uiState.errorMessage ?: "Erro desconhecido",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refreshStats() }) {
                        Text("Tentar Novamente")
                    }
                }
            }
        }

        else -> {
            StatsContent(
                uiState = uiState,
            )
        }
    }
}

@Composable
private fun StatsContent(
    uiState: StatsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        StatsHeader()

        Spacer(modifier = Modifier.height(24.dp))

        StatsCardsSection(
            textsWritten = uiState.textsWritten,
            totalXp = uiState.totalXp,
            textsToday = uiState.textsToday,
            textsGoal = uiState.textsGoal,
            streak = uiState.streak
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.dailyMissions.isNotEmpty()) {
            StatsSection(title = "Missões Diárias") {
                uiState.dailyMissions.forEach { mission ->
                    MissionCard(
                        title = mission.title,
                        description = mission.description,
                        rewardText = mission.rewardText,
                        isCompleted = mission.isCompleted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (uiState.achievements.isNotEmpty()) {
            StatsSection(title = "Conquistas") {
                uiState.achievements.forEach { achievement ->
                    println(achievement.toString())
                    AchievementCard(
                        title = achievement.title,
                        description = achievement.description,
                        progressText = achievement.progressText,
                        rewardText = achievement.rewardText,
                        isCompleted = achievement.isCompleted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

// componentes reutilizaveis

@Composable
private fun StatsHeader() {
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
        modifier = Modifier.padding(start = 40.dp)
    )
}

@Composable
private fun StatsCardsSection(
    textsWritten: Int,
    totalXp: Int,
    textsToday: Int,
    textsGoal: Int,
    streak: Int
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                icon = Icons.Default.Description,
                value = "$textsWritten",
                label = "textos",
                color = Color(0xFF9C27B0),
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = Icons.Default.EmojiEvents,
                value = "$totalXp",
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
                value = "$textsToday",
                label = "Textos hoje",
                color = Color(0xFF008FC8),
                modifier = Modifier.weight(1f),
                showGoal = true,
                goalText = "Meta: $textsGoal textos"
            )
            StatCard(
                icon = Icons.Default.LocalFireDepartment,
                value = "$streak",
                label = "ofensiva",
                color = Color(0xFFFF5722),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        content()
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

@Composable
fun AchievementCard(
    title: String,
    description: String,
    progressText: String,
    rewardText: String,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AchievementIcon(isCompleted = isCompleted)

            Spacer(modifier = Modifier.width(16.dp))

            AchievementContent(
                title = title,
                description = description,
                progressText = progressText,
                rewardText = rewardText,
                isCompleted = isCompleted,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AchievementIcon(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (isCompleted) {
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                } else {
                    Color(0xFFFFC107).copy(alpha = 0.2f)
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isCompleted) {
                Icons.Default.CheckCircle
            } else {
                Icons.Default.WorkspacePremium
            },
            contentDescription = null,
            tint = if (isCompleted) {
                Color(0xFF4CAF50)
            } else {
                Color(0xFFFFC107)
            },
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun AchievementContent(
    title: String,
    description: String,
    progressText: String,
    rewardText: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isCompleted) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            if (isCompleted) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = progressText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = if (isCompleted) "Resgatado!" else rewardText,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MissionCard(
    title: String,
    description: String,
    rewardText: String,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MissionIcon(isCompleted = isCompleted)

            Spacer(modifier = Modifier.width(16.dp))

            MissionContent(
                title = title,
                description = description,
                rewardText = rewardText,
                isCompleted = isCompleted,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MissionIcon(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (isCompleted) {
                    Color(0xFF4CAF50).copy(alpha = 0.2f)
                } else {
                    Color(0xFF2196F3).copy(alpha = 0.2f)
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isCompleted) {
                Color(0xFF4CAF50)
            } else {
                Color(0xFF2196F3)
            },
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun MissionContent(
    title: String,
    description: String,
    rewardText: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isCompleted) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            if (isCompleted) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isCompleted) "Resgatada!" else rewardText,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )
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
            StatsContent(
                uiState = StatsUiState(
                    textsWritten = 42,
                    totalXp = 1250,
                    textsToday = 50,
                    textsGoal = 150,
                    streak = 7,
                    achievements = listOf(
                        UiAchievement(
                            title = "Primeira Palavra",
                            description = "Escreva seu primeiro texto",
                            progress = 1f,
                            progressText = "1/1 textos",
                            rewardText = "Recompensa: +10 XP",
                            iconName = "target",
                            isCompleted = true
                        ),
                        UiAchievement(
                            title = "100 Palavras",
                            description = "Escreva um texto com 100+ palavras",
                            progress = 0f,
                            progressText = "0/100 palavras",
                            rewardText = "Recompensa: +50 XP",
                            iconName = "description",
                            isCompleted = false
                        )
                    ),
                    dailyMissions = listOf(
                        UiDailyMission(
                            title = "Escreva seu primeiro texto",
                            description = "Comece o dia escrevendo!",
                            progress = 1f,
                            progressText = "1/1 texto",
                            rewardText = "+50 XP",
                            iconName = "edit",
                            renovaEm = "13h",
                            isCompleted = true
                        )
                    )
                )
            )
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
            StatsContent(
                uiState = StatsUiState(
                    textsWritten = 42,
                    totalXp = 1250,
                    textsToday = 50,
                    textsGoal = 150,
                    streak = 7
                )
            )
        }
    }
}