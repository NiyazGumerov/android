package ru.itis.homework6.data.db.repository

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.homework6.USER_ID_KEY
import ru.itis.homework6.data.db.model.TrackWithArtists
import ru.itis.homework6.data.db.model.TrackWithUsers
import ru.itis.homework6.data.db.crossref.TrackArtistCrossRef
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef
import ru.itis.homework6.data.db.dao.TrackArtistCrossRefDAO
import ru.itis.homework6.data.db.dao.TrackDAO
import ru.itis.homework6.data.db.dao.UserTrackCrossRefDAO
import ru.itis.homework6.data.db.entities.TrackEntity
import ru.itis.homework6.screens.convertToSeconds
import java.util.UUID
import kotlin.time.Duration

class TrackRepository(
    private val userSharedPreferences: SharedPreferences,
    private val trackDAO: TrackDAO,
    private val trackArtistCrossRefDAO: TrackArtistCrossRefDAO,
    private val userTrackCrossRefDAO: UserTrackCrossRefDAO,
    private val ioDispatcher: CoroutineDispatcher

) {
    suspend fun getTrackById(id: String): TrackEntity? {
        return withContext(ioDispatcher) {
            trackDAO.getTrackById(trackId = id)
                ?: throw IllegalStateException("Track with given id not found")
        }
    }

    suspend fun saveTrack(track: TrackEntity) {
        return withContext(ioDispatcher) {
            trackDAO.saveTrackData(track = track)
        }
    }

    suspend fun saveTrackWithArtists(trackName: String, trackDuration: Int, releaseDate: String, artistIds: List<String>) {
        val id = UUID.randomUUID().toString()
        return withContext(ioDispatcher) {
            trackDAO.saveTrackData(TrackEntity(
                id = id,
                trackName = trackName,
                trackDuration = trackDuration,
                releaseDate = releaseDate
            ))
            artistIds.forEach { artistId ->
                trackArtistCrossRefDAO.insert(TrackArtistCrossRef(id, artistId))
                // связь между треком и артистом/артистами т.к. один трек могут исполнять несколько артистов (feat. ...)
            }
            userSharedPreferences.getString(USER_ID_KEY, "")?.let {
                userTrackCrossRefDAO.insert(UserTrackCrossRef(it, id))
            }
            // чтобы трек, который пользователь добавил, сразу добавлялся в его треки
        }
    }

    suspend fun getTrackWithArtists(trackId: String): TrackWithArtists {
        return withContext(ioDispatcher) {
            trackDAO.getTrackWithArtists(trackId)
        }
    }

    suspend fun getTrackWithUsers(trackId: String): TrackWithUsers {
        return withContext(ioDispatcher) {
            trackDAO.getTrackWithUsers(trackId)
        }
    }

    suspend fun getThreeMostPopularTracks(): List<TrackWithArtists> {
        return withContext(ioDispatcher) {
            userTrackCrossRefDAO.getThreeMostPopularTracks()
                .map { trackId -> trackDAO.getTrackWithArtists(trackId) }
        }
    }

    suspend fun getFourRandomTracks(): List<TrackWithArtists> {
        return withContext(ioDispatcher) {
            trackDAO.getFourRandomTracks().map { trackId -> trackDAO.getTrackWithArtists(trackId) }
        }
    }
}