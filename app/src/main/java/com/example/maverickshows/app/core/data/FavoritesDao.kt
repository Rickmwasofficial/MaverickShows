package com.example.maverickshows.app.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(favorite: FavoritesEntity)

    @Update
    suspend fun update(favorite: FavoritesEntity)

    @Delete
    suspend fun delete(favorite: FavoritesEntity)

    @Query("SELECT * FROM Favorites ORDER BY created_at DESC")
    fun getAllFavorites(): Flow<List<FavoritesEntity>>

    @Query("DELETE FROM Favorites")
    suspend fun deleteAllRows()
}
