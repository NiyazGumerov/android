package ru.itis.homework6.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.homework6.data.db.crossref.TrackArtistCrossRef
import ru.itis.homework6.data.db.crossref.UserArtistCrossRef
import ru.itis.homework6.data.db.crossref.UserTrackCrossRef
import ru.itis.homework6.data.db.dao.ArtistDAO
import ru.itis.homework6.data.db.dao.TrackArtistCrossRefDAO
import ru.itis.homework6.data.db.dao.TrackDAO
import ru.itis.homework6.data.db.dao.UserArtistCrossRefDAO
import ru.itis.homework6.data.db.dao.UserDAO
import ru.itis.homework6.data.db.dao.UserTrackCrossRefDAO
import ru.itis.homework6.data.db.entities.ArtistEntity
import ru.itis.homework6.data.db.entities.TrackEntity
import ru.itis.homework6.data.db.entities.UserEntity

@Database(
    entities = [UserEntity::class, TrackEntity::class, ArtistEntity::class, UserTrackCrossRef::class,
        UserArtistCrossRef::class, TrackArtistCrossRef::class],
    version = 1
)
abstract class InceptionDatabase : RoomDatabase() {
    abstract val userDAO: UserDAO
    abstract val trackDAO: TrackDAO
    abstract val artistDAO: ArtistDAO
    abstract val userArtistCrossRefDAO: UserArtistCrossRefDAO
    abstract val userTrackCrossRefDAO: UserTrackCrossRefDAO
    abstract val trackArtistCrossRefDAO: TrackArtistCrossRefDAO
}