package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.itis.homework6.data.db.model.TrackWithArtists
import ru.itis.homework6.data.db.model.TrackWithUsers

import ru.itis.homework6.data.db.entities.TrackEntity

@Dao
interface TrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrackData(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE :trackId = track_id")
    suspend fun getTrackById(trackId: String): TrackEntity?

    @Transaction
    @Query("SELECT * FROM tracks WHERE track_id = :trackId")
    suspend fun getTrackWithArtists(trackId: String): TrackWithArtists

    @Transaction
    @Query("SELECT * FROM tracks WHERE track_id = :trackId")
    suspend fun getTrackWithUsers(trackId: String): TrackWithUsers

    @Query("SELECT track_id FROM tracks ORDER BY RANDOM() LIMIT 4")
    suspend fun getFourRandomTracks(): List<String>

}
