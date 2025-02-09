package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.TrackArtistCrossRef
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.TrackEntity

data class TrackWithArtists(
    @Embedded val track: TrackEntity,
    @Relation(
        parentColumn = "track_id",
        entityColumn = "artist_id",
        associateBy = Junction(TrackArtistCrossRef::class)
    )
    val artists: List<ArtistEntity>
)
