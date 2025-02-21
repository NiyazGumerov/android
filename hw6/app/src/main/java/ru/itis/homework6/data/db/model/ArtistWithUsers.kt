package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.UserArtistCrossRef
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.UserEntity

data class ArtistWithUsers(
    @Embedded val artist: ArtistEntity,
    @Relation(
        parentColumn = "artist_id",
        entityColumn = "user_id",
        associateBy = Junction(UserArtistCrossRef::class)
    )
    val users: List<UserEntity>
)