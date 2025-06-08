package com.example.maverickshows.app.search.data

import com.example.maverickshows.app.core.data.RecentDao
import com.example.maverickshows.app.core.data.RecentSearchEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData
import kotlinx.coroutines.flow.Flow

class SearchRepImpl(
    private val api: TmdbAPI,
    private val searchDao: RecentDao
): SearchRep {
    override suspend fun getSearchResults(q: String): List<HomeData> {
        return (api.getMovieSearch(q = q).results.map { it.toHomeData() } + api.getTvSearch(q = q).results.map { it.toHomeData() })
    }
    override suspend fun getAllGenres(): List<Genre> {
        // Genres are usually robust, but added filter for safety based on your models
        return (api.getTvGenres().genres.filter { it.id != null && it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.id != null && it.name.isNotBlank() })
    }

    override suspend fun getAllSearchedItemsStream(): Flow<List<RecentSearchEntity>> {
        return searchDao.getAllSearches()
    }

    override suspend fun insertSearchedItem(item: RecentSearchEntity): String {
        try {
            searchDao.insert(item)
            return "Saved Successfully"
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    override suspend fun deleteItem(item: RecentSearchEntity): String {
        try {
            searchDao.delete(item)
            return "Deleted Successfully"
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    override suspend fun getSavedShows(saved: List<RecentSearchEntity>): List<HomeData> {
        var savedData: MutableList<HomeData> = mutableListOf()
        for (data in saved) {
            if (data.type == "tv") {
                savedData.add(api.getTvById(id = data.id).toHomeData())
            } else {
                savedData.add(api.getMovieById(id = data.id).toHomeData())
            }
        }
        return savedData.toList()
    }
}