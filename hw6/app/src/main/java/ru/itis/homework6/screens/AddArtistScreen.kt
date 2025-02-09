package ru.itis.homework6.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.homework6.MainActivity
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.ui.theme.Homework6Theme
import java.util.UUID

@Composable
fun AddArtistScreen(navController: NavController) {
    val context = LocalContext.current
    var artistName by remember { mutableStateOf("") }
    var isArtistNameError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val artistRepository = (context as MainActivity).artistRepository
    val coroutineScope = CoroutineScope(Dispatchers.IO)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.add_new_artist),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = artistName,
            onValueChange = {
                artistName = it
                isArtistNameError = false
                errorMessage = null
            },
            label = { Text(stringResource(id = R.string.artist)) },
            isError = isArtistNameError,
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (artistName.isEmpty()) {
                    isArtistNameError = true
                    errorMessage = context.getString(R.string.artist_is_empty)
                } else {
                    coroutineScope.launch {
                        artistRepository.saveArtist(
                            artistName = artistName
                        )
                    }
                    navController.popBackStack()
                }
            }
        ) {
            Text(stringResource(id = R.string.add))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddArtistScreenPreview() {
    Homework6Theme {
        AddTrackScreen(navController = rememberNavController())
    }
}