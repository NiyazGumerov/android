import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import ru.itis.homework6.navigate.Route
import ru.itis.homework6.ui.theme.Homework6Theme

@Composable
fun ArtistsListScreen(navController: NavController) {
    val context = LocalContext.current
    var favoriteArtists by remember { mutableStateOf(emptyList<ArtistEntity>()) }
    var allArtists by remember { mutableStateOf(emptyList<ArtistEntity>()) }
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val userRepository = (context as MainActivity).userRepository
    val artistRepository = (context as MainActivity).artistRepository

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            favoriteArtists = userRepository.getUserWithArtists()?.artists ?: emptyList()
            allArtists = artistRepository.getAllArtists()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column {

            Button(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.back))
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                items(allArtists) { artist ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = artist.name, modifier = Modifier)
                        IconButton(onClick = {
                            if (favoriteArtists.contains(artist)) {
                                favoriteArtists = favoriteArtists - artist
                                coroutineScope.launch {
                                    userRepository.deleteLikedArtist(artist.id)
                                }
                            } else {
                                favoriteArtists = favoriteArtists + artist
                                coroutineScope.launch {
                                    userRepository.saveLikedArtist(artist.id)
                                }
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.btn_star_big_on),
                                contentDescription = stringResource(id = R.string.add_favorite),
                                tint = if (favoriteArtists.contains(artist)) Color.Red else Color.Gray
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate(Route.ADD_ARTIST_ROUTE.destination) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_new_artist))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArtistsListScreen() {
    Homework6Theme {
        ArtistsListScreen(rememberNavController())
    }
}
