package ru.itis.homework6.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework6.MainActivity
import ru.itis.homework6.R
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.TrackEntity
import ru.itis.homework6.ui.DatePickerField
import ru.itis.homework6.ui.DurationTextField
import ru.itis.homework6.ui.theme.Homework6Theme
import java.util.UUID

@Composable
fun AddTrackScreen(navController: NavController) {
    val context = LocalContext.current
    var trackName by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isTrackNameError by remember { mutableStateOf(false) }
    val trackRepository = (context as MainActivity).trackRepository
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    var availableArtists = remember { mutableStateListOf<ArtistEntity>() }
    var selectedArtists = remember { mutableStateListOf<ArtistEntity>() }
    val artistRepository = (context as MainActivity).artistRepository
    var searchQuery by remember { mutableStateOf("") }
    val filteredArtists =
        availableArtists.filter { it.name.contains(searchQuery, ignoreCase = true) }



    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val artists = artistRepository.getAllArtists()
            withContext(Dispatchers.Main) {
                availableArtists.addAll(artists)
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.add_new_track),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = trackName,
            onValueChange = {
                trackName = it
                isTrackNameError = false
                errorMessage = null
            },
            label = { Text(stringResource(id = R.string.track_name)) },
            isError = isTrackNameError,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DurationTextField(duration = duration) {
            duration = it
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(id = R.string.artist)) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .width(280.dp)
                .height(120.dp)
                .border(1.dp, Color.Gray)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            items(filteredArtists) { artist ->
                val isSelected = artist in selectedArtists
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Blue)
                        .fillMaxWidth()
                        .background(if (isSelected) Color.Gray else Color.Transparent)
                        .clickable {
                            if (isSelected) selectedArtists.remove(artist) else selectedArtists.add(
                                artist
                            )
                        }
                        .padding(8.dp)
                ) {
                    Text(text = artist.name, color = if (isSelected) Color.White else Color.Black)
                }
            }
        }
        DatePickerField(releaseDate = releaseDate) {
            releaseDate = it
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            when {
                trackName.isBlank() -> {
                    isTrackNameError = true
                    errorMessage = context.getString(R.string.track_name_is_empty)
                }

                duration.isBlank() -> {
                    errorMessage = context.getString(R.string.duration_is_empty)
                }

                selectedArtists.isEmpty() -> {
                    errorMessage = context.getString(R.string.artist_is_empty)
                }

                releaseDate.isBlank() -> {
                    errorMessage = context.getString(R.string.release_date_is_empty)
                }


                else -> {
                    coroutineScope.launch {
                        trackRepository.saveTrackWithArtists(
                            trackName = trackName,
                            trackDuration = convertToSeconds(duration),
                            releaseDate = releaseDate,
                            artistIds = selectedArtists.map { it.id }
                        )
                    }
                    navController.popBackStack()
                }
            }
        }) {
            Text(stringResource(id = R.string.add))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTrackScreenPreview() {
    Homework6Theme {
        AddTrackScreen(navController = rememberNavController())
    }
}

fun convertToSeconds(time: String): Int {
    val parts = time.split(":")
    if (parts.size != 2) return 0 // Если формат не "мм:сс", возвращаем 0

    val minutes = parts[0].toIntOrNull() ?: 0
    val seconds = parts[1].toIntOrNull() ?: 0

    return (minutes * 60) + seconds
}