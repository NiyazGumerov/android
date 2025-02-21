package ru.itis.homework6.data.db.repository

import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.homework6.USER_ID_KEY
import ru.itis.homework6.data.crypto.hashPassword
import ru.itis.homework6.data.db.model.UserWithArtists
import ru.itis.homework6.data.db.model.UserWithTracks
import ru.itis.homework6.data.db.crossref.UserArtistCrossRef
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef
import ru.itis.homework6.data.db.dao.UserArtistCrossRefDAO
import ru.itis.homework6.data.db.dao.UserDAO
import ru.itis.homework6.data.db.dao.UserTrackCrossRefDAO
import ru.itis.homework6.data.db.entities.UserEntity
import java.util.UUID

class UserRepository(
    private val userSharedPreferences: SharedPreferences,
    private val userDAO: UserDAO,
    private val userArtistCrossRefDAO: UserArtistCrossRefDAO,
    private val userTrackCrossRefDAO: UserTrackCrossRefDAO,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getUserById(id: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDAO.getUserById(userId = id)
                ?: throw IllegalStateException("User with given id not found")
        }
    }

    suspend fun saveUser(username: String, email: String, password: String) {
        val userId = UUID.randomUUID().toString()
        return withContext(ioDispatcher) {
            userDAO.saveUserData(UserEntity(
                id = userId,
                username = username,
                email = email,
                hashedPassword = hashPassword(password)
            ))
        }
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDAO.getUserByUsername(username = username)
        }
    }

    suspend fun getUserWithTracks(): UserWithTracks? {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return null
        return withContext(ioDispatcher) {
            userDAO.getUserWithTracks(userId)
        }
    }

    suspend fun getUserWithArtists(): UserWithArtists? {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return null
        return withContext(ioDispatcher) {
            userDAO.getUserWithArtists(userId)
        }
    }

    suspend fun saveLikedArtist(artistId: String) {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return
        withContext(ioDispatcher) {
            userArtistCrossRefDAO.insert(UserArtistCrossRef(userId = userId, artistId = artistId))
        }
    }
    suspend fun deleteLikedArtist(artistId: String) {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return
        withContext(ioDispatcher) {
            userArtistCrossRefDAO.delete(UserArtistCrossRef(userId = userId, artistId = artistId))
        }
    }
    suspend fun deleteLikedTrack(trackId: String) {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return
        withContext(ioDispatcher) {
            userTrackCrossRefDAO.delete(UserTrackCrossRef(userId = userId, trackId = trackId))
        }
    }
    suspend fun saveLikedTrack(trackId: String) {
        val userId = userSharedPreferences.getString(USER_ID_KEY, "") ?: return
        withContext(ioDispatcher) {
            userTrackCrossRefDAO.insert(UserTrackCrossRef(userId = userId, trackId = trackId))
        }
    }
}