package ru.itis.homework6.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id: String,
    @ColumnInfo(name = "track_name")
    val trackName: String,
    @ColumnInfo(name = "track_duration")
    val trackDuration: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
)

