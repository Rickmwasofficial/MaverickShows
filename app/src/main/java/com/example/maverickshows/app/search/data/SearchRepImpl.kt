package com.example.maverickshows.app.search.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData

class SearchRepImpl(
    private val api: TmdbAPI
): SearchRep {
    override suspend fun getSearchResults(q: String): List<HomeData> {
        return (api.getMovieSearch(q = q).results.map { it.toHomeData() } + api.getTvSearch(q = q).results.map { it.toHomeData() })
    }
    override suspend fun getAllGenres(): List<Genre> {
        // Genres are usually robust, but added filter for safety based on your models
        return (api.getTvGenres().genres.filter { it.id != null && it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.id != null && it.name.isNotBlank() })
    }
}