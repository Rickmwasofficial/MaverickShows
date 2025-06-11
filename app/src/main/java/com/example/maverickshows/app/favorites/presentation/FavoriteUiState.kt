package com.example.maverickshows.app.favorites.presentation

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

interface FavoriteUiState {
    data class Success(
        val genres: List<Genre>,
        val favorites: List<HomeData>
    ): FavoriteUiState
    object Loading: FavoriteUiState
    data class Error(
        val message: String
    ): FavoriteUiState
}