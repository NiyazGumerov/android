package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef

@Dao
interface UserTrackCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userTrackCrossRef: UserTrackCrossRef)

    @Delete
    suspend fun delete(userTrackCrossRef: UserTrackCrossRef)

    @Query("SELECT track_id FROM user_track GROUP BY track_id ORDER BY COUNT(*) DESC LIMIT 3")
    suspend fun getThreeMostPopularTracks(): List<String>
}