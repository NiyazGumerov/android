package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef
import ru.itis.homework6.data.db.entities.TrackEntity
import ru.itis.homework6.data.db.entities.UserEntity

data class UserWithTracks(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "track_id",
        associateBy = Junction(UserTrackCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
