package com.example.maverickshows.app.details.presentation

import com.example.maverickshows.app.details.domain.DetailData

sealed interface DetailUiState {
    data class Success(
        val data: DetailData
    ): DetailUiState
    object Loading: DetailUiState
    data class Error(
        val message: String
    ): DetailUiState
}