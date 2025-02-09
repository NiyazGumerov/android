package ru.itis.homework6.data.db.crossref

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.UserEntity

@Entity(
    tableName = "user_artist",
    primaryKeys = ["user_id", "artist_id"]
)
data class UserArtistCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "artist_id")
    val artistId: String
)