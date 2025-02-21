package ru.itis.homework6.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ru.itis.homework6.App
import ru.itis.homework6.MainActivity
import ru.itis.homework6.R
import ru.itis.homework6.USER_ID_KEY
import ru.itis.homework6.data.db.model.TrackWithArtists
import ru.itis.homework6.navigate.Route
import ru.itis.homework6.ui.TrackItem
import ru.itis.homework6.ui.theme.Homework6Theme

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TracksListScreen(navController: NavController) {
    val context = LocalContext.current
    var tracksWithArtists = remember { mutableStateListOf<TrackWithArtists>() }
    val userRepository = (context as MainActivity).userRepository
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val trackRepository = (context as MainActivity).trackRepository

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val tracksWithoutArtists = userRepository.getUserWithTracks()?.tracks ?: emptyList()
            for (track in tracksWithoutArtists) {
                val trackWithArtists = trackRepository.getTrackWithArtists(track.id)
                withContext(Dispatchers.Main) {
                    tracksWithArtists.add(trackWithArtists)
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    (context.applicationContext as App).getPreferences().edit().remove(USER_ID_KEY)
                        .apply()
                    navController.navigate(Route.AUTH_ROUTE.destination)
                }) { Text(stringResource(id = R.string.logout)) }
                Button(onClick = { navController.navigate(Route.ARTISTS_LIST_ROUTE.destination) }) {
                    Text(stringResource(id = R.string.artists_list))

                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { navController.navigate(Route.RECOMMENDATIONS_ROUTE.destination) }
                ) {
                    Text(stringResource(id = R.string.recomendations))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ){
                items(tracksWithArtists) { trackWithArtists ->
                    TrackItem(trackWithArtists = trackWithArtists) {
                        coroutineScope.launch {
                            userRepository.deleteLikedTrack(it.track.id)
                            tracksWithArtists.remove(it)
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate(Route.ADD_TRACK_ROUTE.destination) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_new_track))
        }

    }
}

fun convertToDurationFormat(time: Int): String {
    val minutes = (time / 60).toString()
    val seconds = (time % 60).toString().padStart(2, '0')


    return "$minutes:$seconds"
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Homework6Theme {
        TracksListScreen(navController = rememberNavController())
    }
}
