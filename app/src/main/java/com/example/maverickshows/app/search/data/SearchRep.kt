package com.example.maverickshows.app.search.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

interface SearchRep {
    suspend fun getSearchResults(q: String): List<HomeData>
    suspend fun getAllGenres(): List<Genre>
}