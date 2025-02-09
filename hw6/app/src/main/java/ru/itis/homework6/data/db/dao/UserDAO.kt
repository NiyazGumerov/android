package ru.itis.homework6.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.itis.homework6.data.db.model.UserWithArtists
import ru.itis.homework6.data.db.model.UserWithTracks

import ru.itis.homework6.data.db.entities.UserEntity


@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserData(user: UserEntity)

    @Query("SELECT * FROM users WHERE :userId = user_id")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE :username = username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserWithArtists(userId: String): UserWithArtists

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserWithTracks(userId: String): UserWithTracks

}