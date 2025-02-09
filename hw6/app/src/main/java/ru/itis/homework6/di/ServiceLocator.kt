package ru.itis.homework6.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import ru.itis.homework6.data.db.InceptionDatabase
import ru.itis.homework6.data.db.repository.ArtistRepository
import ru.itis.homework6.data.db.repository.TrackRepository
import ru.itis.homework6.data.db.repository.UserRepository

object ServiceLocator {

    private const val DATABASE_NAME = "InceptionDB"

    private var dbInstance: InceptionDatabase? = null

    private var userRepository: UserRepository? = null
    private var trackRepository: TrackRepository? = null
    private var artistRepository: ArtistRepository? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, DATABASE_NAME)
            .build()

    }

    fun initDataLayerDependencies(ctx: Context, sharedPreferences: SharedPreferences) {
        if (dbInstance == null) {
            initDatabase(ctx)
            dbInstance?.let {
                userRepository = UserRepository(sharedPreferences,it.userDAO, it.userArtistCrossRefDAO, it.userTrackCrossRefDAO, Dispatchers.IO)
                trackRepository = TrackRepository(sharedPreferences, it.trackDAO, it.trackArtistCrossRefDAO, it.userTrackCrossRefDAO, Dispatchers.IO)
                artistRepository = ArtistRepository(it.artistDAO, Dispatchers.IO)
            }
        }
    }

    fun getUserRepository(): UserRepository =
        userRepository ?: throw IllegalStateException("User repository is not initialized")

    fun getTrackRepository(): TrackRepository =
        trackRepository ?: throw IllegalStateException("Track repository is not initialized")

    fun getArtistRepository(): ArtistRepository =
        artistRepository ?: throw IllegalStateException("Artist repository is not initialized")
}