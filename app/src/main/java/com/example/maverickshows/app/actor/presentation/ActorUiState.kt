package com.example.maverickshows.app.actor.presentation

import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.core.models.ActorImages

sealed interface ActorUiState {
    data class Success(
        val data: ActorData,
        val img: ActorImages
    ): ActorUiState
    object Loading: ActorUiState
    data class Error(
        val message: String
    ): ActorUiState
}