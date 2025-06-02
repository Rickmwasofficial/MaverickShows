package com.example.maverickshows.app.home.presentation

import com.example.maverickshows.app.home.domain.HomeData

sealed interface HomeUIState {
    data class Success(
        val allTrending: List<HomeData>,
        val allPopular: List<HomeData>,
        val allTopRated: List<HomeData>): HomeUIState
    object Loading: HomeUIState
    data class Error(
        val message: String
    ): HomeUIState
}