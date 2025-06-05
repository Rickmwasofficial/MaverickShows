package com.example.maverickshows.app.details.presentation

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.details.domain.DetailCredits
import com.example.maverickshows.app.details.domain.DetailData
import com.example.maverickshows.app.home.domain.HomeData

sealed interface DetailUiState {
    data class Success(
        val data: DetailData,
        val genres: List<Genre>,
        val imgData: ImageData,
        val credits: List<DetailCredits>,
        val recommendations: List<HomeData>
    ): DetailUiState
    object Loading: DetailUiState
    data class Error(
        val message: String
    ): DetailUiState
}