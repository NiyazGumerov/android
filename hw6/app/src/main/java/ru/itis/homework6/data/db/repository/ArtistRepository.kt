package ru.itis.homework6.data.db.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.homework6.data.db.model.ArtistWithTracks
import ru.itis.homework6.data.db.model.ArtistWithUsers
import ru.itis.homework6.data.db.dao.ArtistDAO
import ru.itis.homework6.data.db.entities.ArtistEntity
import java.util.UUID

class ArtistRepository(
    private val artistDAO: ArtistDAO,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getArtistById(id: String): ArtistEntity? {
        return withContext(ioDispatcher) {
            artistDAO.getArtistById(artistId = id)
                ?: throw IllegalStateException("User with given id not found")
        }
    }

    suspend fun saveArtist(artistName: String) {
        val id = UUID.randomUUID().toString()
        return withContext(ioDispatcher) {
            artistDAO.saveArtistData(artist = ArtistEntity(id, artistName))
        }
    }

    suspend fun getArtistWithTracks(artistId: String): List<ArtistWithTracks> {
        return withContext(ioDispatcher) {
            artistDAO.getArtistWithTracks(artistId)
        }
    }

    suspend fun getArtistWithUsers(artistId: String): List<ArtistWithUsers> {
        return withContext(ioDispatcher) {
            artistDAO.getArtistWithUsers(artistId)
        }
    }

    suspend fun getAllArtists(): List<ArtistEntity> {
        return withContext(ioDispatcher) {
            artistDAO.getAllArtists()
        }
    }
}