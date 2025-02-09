package ru.itis.homework6.navigate

import ArtistsListScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.ActivityNavigator
import ru.itis.homework6.screens.AuthScreen
import ru.itis.homework6.screens.RegisterScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.itis.homework6.App
import ru.itis.homework6.USER_ID_KEY
import ru.itis.homework6.screens.AddArtistScreen
import ru.itis.homework6.screens.AddTrackScreen
import ru.itis.homework6.screens.RecomendationsScreen
import ru.itis.homework6.screens.TracksListScreen



@Composable
fun AppNavigation() {
    val context = LocalContext.current.applicationContext
    var startDestination = Route.AUTH_ROUTE.destination
    (context as App).getPreferences().let { preferences ->
        if (preferences.contains(USER_ID_KEY)) {
            startDestination = Route.TRACK_LIST_ROUTE.destination
        }
    }
    val navController = rememberNavController()
    NavHost(navController, startDestination = startDestination) {
        composable(Route.AUTH_ROUTE.destination) { AuthScreen(navController) }
        composable(Route.REGISTER_ROUTE.destination) { RegisterScreen(navController) }
        composable(Route.TRACK_LIST_ROUTE.destination) { TracksListScreen(navController) }
        composable(Route.ADD_TRACK_ROUTE.destination) {AddTrackScreen(navController)}
        composable(Route.ADD_ARTIST_ROUTE.destination) {AddArtistScreen(navController)}
        composable(Route.ARTISTS_LIST_ROUTE.destination) {ArtistsListScreen(navController)}
        composable(Route.RECOMMENDATIONS_ROUTE.destination) {RecomendationsScreen(navController)}
    }
}
enum class Route(val destination: String){
    AUTH_ROUTE("auth"),
    TRACK_LIST_ROUTE("trackList"),
    REGISTER_ROUTE("register"),
    ADD_TRACK_ROUTE("addTrack"),
    ADD_ARTIST_ROUTE("addArtist"),
    ARTISTS_LIST_ROUTE("artistsList"),
    RECOMMENDATIONS_ROUTE("recommendations"),
}