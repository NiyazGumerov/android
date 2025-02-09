package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef
import ru.itis.homework6.data.db.entities.TrackEntity
import ru.itis.homework6.data.db.entities.UserEntity

data class TrackWithUsers(
    @Embedded val track: TrackEntity,
    @Relation(
        parentColumn = "track_id",
        entityColumn = "user_id",
        associateBy = Junction(UserTrackCrossRef::class)
    )
    val users: List<UserEntity>
)
