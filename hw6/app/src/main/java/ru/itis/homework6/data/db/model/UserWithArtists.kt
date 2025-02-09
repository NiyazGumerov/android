package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.UserArtistCrossRef
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.UserEntity

data class UserWithArtists(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "artist_id",
        associateBy = Junction(UserArtistCrossRef::class)
    )
    val artists: List<ArtistEntity>
)
