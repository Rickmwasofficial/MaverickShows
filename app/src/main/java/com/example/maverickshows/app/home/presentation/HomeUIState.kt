package com.example.maverickshows.app.home.presentation

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

sealed interface HomeUIState {
    data class Success(
        val nextPage: Int = 2,
        val allTrending: List<HomeData>,
        val allPopular: List<HomeData>,
        val allTopRated: List<HomeData>,
        val upcomingMovies: List<HomeData>,
        val airingTv: List<HomeData>,
        val onAirTv: List<HomeData>,
        val nowPlaying: List<HomeData>,
        val isRefreshing: Boolean = false,
        val genres: List<Genre>
    ): HomeUIState
    object Loading: HomeUIState
    data class Error(
        val message: String
    ): HomeUIState
}

sealed interface ContentUIState {
    object All: ContentUIState
    object Movie: ContentUIState
    object Series: ContentUIState
}