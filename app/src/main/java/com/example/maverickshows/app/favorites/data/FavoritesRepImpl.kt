package com.example.maverickshows.app.favorites.data

import com.example.maverickshows.app.core.data.FavoritesDao
import com.example.maverickshows.app.core.data.FavoritesEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData
import kotlinx.coroutines.flow.Flow

class FavoritesRepImpl(
    private val api: TmdbAPI,
    private val favoritesDao: FavoritesDao
): FavoritesRep {
    override suspend fun getAllGenres(): List<Genre> {
        return (api.getTvGenres().genres.filter { it.id != null && it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.id != null && it.name.isNotBlank() })
    }

    override fun getAllLikedItemsStream(): Flow<List<FavoritesEntity>> {
        return favoritesDao.getAllFavorites()
    }

    override suspend fun getLikedShows(saved: List<FavoritesEntity>): List<HomeData> {
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

    override suspend fun deleteAllItems(): String {
        try {
            favoritesDao.deleteAllRows()
            return "Successful"
        } catch (e: Exception) {
            return e.message.toString()
        }
    }
}