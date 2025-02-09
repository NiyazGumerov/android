package ru.itis.homework6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ru.itis.homework6.di.ServiceLocator
import ru.itis.homework6.navigate.AppNavigation
import ru.itis.homework6.ui.theme.Homework6Theme

class MainActivity : ComponentActivity() {
    val userRepository = ServiceLocator.getUserRepository()
    val trackRepository = ServiceLocator.getTrackRepository()
    val artistRepository = ServiceLocator.getArtistRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework6Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        AppNavigation()
    }
}

