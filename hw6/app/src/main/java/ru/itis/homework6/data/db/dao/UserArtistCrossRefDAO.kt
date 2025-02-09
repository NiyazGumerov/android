package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ru.itis.homework6.data.db.crossref.UserArtistCrossRef

@Dao
interface UserArtistCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userArtistCrossRef: UserArtistCrossRef)

    @Delete
    suspend fun delete(userArtistCrossRef: UserArtistCrossRef)
}