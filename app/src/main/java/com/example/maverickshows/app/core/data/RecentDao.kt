package com.example.maverickshows.app.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(recent: RecentSearchEntity)

    @Update
    suspend fun update(recent: RecentSearchEntity)

    @Delete
    suspend fun delete(recent: RecentSearchEntity)

    @Query("SELECT * FROM `Recent Search` ORDER BY created_at ASC")
    fun getAllSearches(): Flow<List<RecentSearchEntity>>
}