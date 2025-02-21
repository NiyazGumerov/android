package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.itis.homework6.data.db.model.ArtistWithTracks
import ru.itis.homework6.data.db.model.ArtistWithUsers
import ru.itis.homework6.data.db.entities.ArtistEntity

@Dao
interface ArtistDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtistData(artist: ArtistEntity)

    @Query("SELECT * FROM artists WHERE :artistId = artist_id")
    suspend fun getArtistById(artistId: String): ArtistEntity?

    @Query("SELECT * FROM artists")
    suspend fun getAllArtists(): List<ArtistEntity>

    @Transaction
    @Query("SELECT * FROM artists WHERE artist_id = :artistId")
    suspend fun getArtistWithTracks(artistId: String): List<ArtistWithTracks>

    @Transaction
    @Query("SELECT * FROM artists WHERE artist_id = :artistId")
    suspend fun getArtistWithUsers(artistId: String): List<ArtistWithUsers>

}