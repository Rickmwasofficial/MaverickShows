package com.example.maverickshows.app.search.data

import com.example.maverickshows.app.core.data.RecentSearchEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData
import kotlinx.coroutines.flow.Flow

interface SearchRep {
    suspend fun getSearchResults(q: String): List<HomeData>
    suspend fun getAllGenres(): List<Genre>
    suspend fun getAllSearchedItemsStream(): Flow<List<RecentSearchEntity>>
    suspend fun insertSearchedItem(item: RecentSearchEntity): String
    suspend fun deleteItem(item: RecentSearchEntity): String
    suspend fun getSavedShows(saved: List<RecentSearchEntity>): List<HomeData>
    suspend fun deleteAllItems(): String
}