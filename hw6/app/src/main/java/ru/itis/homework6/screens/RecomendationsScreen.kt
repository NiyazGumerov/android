package ru.itis.homework6.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework6.MainActivity
import ru.itis.homework6.R
import ru.itis.homework6.data.db.model.TrackWithArtists
import ru.itis.homework6.navigate.Route
import ru.itis.homework6.ui.theme.Homework6Theme

@Composable
fun RecomendationsScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var newTracksWithArtists = remember { mutableStateListOf<TrackWithArtists>() }
    var favoriteTracksWithArtists = remember { mutableStateListOf<TrackWithArtists>() }
    val trackRepository = (context as MainActivity).trackRepository
    val userRepository = (context as MainActivity).userRepository



    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val likedTracks = userRepository.getUserWithTracks()?.tracks ?: emptyList()
            var recomendTracksWithArtists =
                trackRepository.getThreeMostPopularTracks() + trackRepository.getFourRandomTracks()
            withContext(Dispatchers.Main) {
                for (trackWithArtists in recomendTracksWithArtists) {
                    if (!newTracksWithArtists.contains(trackWithArtists)
                        && !likedTracks.contains(trackWithArtists.track)
                    ) {
                        newTracksWithArtists.add(trackWithArtists)
                    }
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        Button(onClick = { navController.popBackStack() }) {
            Text(text = stringResource(id = R.string.back))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(newTracksWithArtists) { newTrackWithArtists ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier.weight(1f) // Заполняет доступное пространство
                            ) {
                                Text(
                                    text = newTrackWithArtists.track.trackName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = newTrackWithArtists.artists.joinToString { it.name },
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )

                            }

                            // Длительность и дата справа
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = convertToDurationFormat(newTrackWithArtists.track.trackDuration),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Blue
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = newTrackWithArtists.track.releaseDate,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            IconButton(onClick = {
                                if (favoriteTracksWithArtists.contains(newTrackWithArtists)) {
                                    coroutineScope.launch {
                                        userRepository.deleteLikedTrack(newTrackWithArtists.track.id)
                                    }
                                    favoriteTracksWithArtists.remove(newTrackWithArtists)
                                } else {
                                    favoriteTracksWithArtists.add(newTrackWithArtists)
                                    coroutineScope.launch {
                                        userRepository.saveLikedTrack(newTrackWithArtists.track.id)
                                    }
                                    favoriteTracksWithArtists.add(newTrackWithArtists)

                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.btn_star_big_on),
                                    contentDescription = stringResource(id = R.string.add_favorite),
                                    tint = if (favoriteTracksWithArtists.contains(newTrackWithArtists)) {
                                        Color.Red
                                    } else {
                                        Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecomendationsScreenPreview() {
    Homework6Theme {
        RecomendationsScreen(rememberNavController())
    }
}