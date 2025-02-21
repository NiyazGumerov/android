package ru.itis.homework6.data.db.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "user_track",
    primaryKeys = ["user_id", "track_id"],
)
data class UserTrackCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "track_id")
    val trackId: String
)
