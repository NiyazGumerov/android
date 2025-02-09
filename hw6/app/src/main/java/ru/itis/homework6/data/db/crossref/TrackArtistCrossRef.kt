package ru.itis.homework6.data.db.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.TrackEntity

@Entity(
    tableName = "track_artist",
    primaryKeys = ["track_id", "artist_id"],
)
data class TrackArtistCrossRef(
    @ColumnInfo(name = "track_id")
    val trackId: String,
    @ColumnInfo(name = "artist_id")
    val artistId: String
)