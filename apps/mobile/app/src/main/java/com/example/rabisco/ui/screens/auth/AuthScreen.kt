package com.example.rabisco.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rabisco.domain.repositories.AchievementsRepository
import com.example.rabisco.domain.repositories.AuthRepository
import com.example.rabisco.ui.components.Container
import com.example.rabisco.ui.screens.auth.components.FooterSection
import com.example.rabisco.ui.screens.auth.components.HeaderSection
import com.example.rabisco.ui.theme.RabiscoTheme
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Entrar", "Cadastrar")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        HeaderSection()

        Spacer(Modifier.height(32.dp))

        Spacer(Modifier.height(24.dp))

        Container{
            Column(
                Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SingleChoiceSegmentedButtonRow(
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size),
                            icon = {},
                            label = { Text(label) },
                            modifier = Modifier.border(0.dp, color = Color.Transparent),
                            border = BorderStroke(0.dp, Color.Transparent)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                if (selectedIndex == 0)
                    SignInScreen()
                else
                    SignUpScreen()
            }
        }

        Spacer(Modifier.height(32.dp))

        FooterSection()
    }
}


@Preview
@Composable
fun AuthPreview() {
    RabiscoTheme {
        AuthScreen()
    }
}