package com.example.rabisco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
<<<<<<< HEAD
import com.example.rabisco.navigation.AppNavigation
=======
import com.example.rabisco.navigation.AppNavHost
>>>>>>> cf9cc2f247713a2742ed9a4ab8fc34874f50cb5b
import com.example.rabisco.ui.theme.RabiscoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RabiscoTheme {
<<<<<<< HEAD
                AppNavigation()
=======
                AppNavHost()
>>>>>>> cf9cc2f247713a2742ed9a4ab8fc34874f50cb5b
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RabiscoTheme {
<<<<<<< HEAD
        Greeting("Daniel")
=======
        AppNavHost()
>>>>>>> cf9cc2f247713a2742ed9a4ab8fc34874f50cb5b
    }
}