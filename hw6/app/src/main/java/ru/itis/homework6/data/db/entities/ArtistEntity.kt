package ru.itis.homework6.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
)
