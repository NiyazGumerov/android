package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework6.data.db.crossref.TrackArtistCrossRef

@Dao
interface TrackArtistCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackArtistCrossRef: TrackArtistCrossRef)

    @Delete
    suspend fun delete(trackArtistCrossRef: TrackArtistCrossRef)

}