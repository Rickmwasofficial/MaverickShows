package com.example.maverickshows.app.search.presentation

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

interface SearchUIState {
    data class Idle(
        val recentSearches: List<HomeData>
    ): SearchUIState
    data class Success(
        val data: List<HomeData>,
        val genres: List<Genre>,
        val recentSearches: List<HomeData>
    ): SearchUIState
    object Loading: SearchUIState
    data class Error(
        val message: String
    ): SearchUIState
}