package ru.itis.homework6.data.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.itis.homework6.data.db.crossref.TrackArtistCrossRef
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.TrackEntity

data class ArtistWithTracks(
    @Embedded val artist: ArtistEntity,
    @Relation(
        parentColumn = "artist_id",
        entityColumn = "track_id",
        associateBy = Junction(TrackArtistCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
