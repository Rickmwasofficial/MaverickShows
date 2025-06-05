package com.example.maverickshows.app.actor.presentation

import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.actor.domain.FilmographyData
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.Genre

sealed interface ActorUiState {
    data class Success(
        val data: ActorData,
        val img: ActorImages,
        val filmographyData: List<FilmographyData>,
        val genre: List<Genre>
    ): ActorUiState
    object Loading: ActorUiState
    data class Error(
        val message: String
    ): ActorUiState
}