package com.example.maverickshows.app.favorites.data

import com.example.maverickshows.app.core.data.FavoritesEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData
import kotlinx.coroutines.flow.Flow

interface FavoritesRep {
    suspend fun getAllGenres(): List<Genre>
    fun getAllLikedItemsStream(): Flow<List<FavoritesEntity>>
    suspend fun getLikedShows(saved: List<FavoritesEntity>): List<HomeData>
    suspend fun deleteAllItems(): String
}