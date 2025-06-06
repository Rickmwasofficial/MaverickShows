package com.example.maverickshows.app.actor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.actor.domain.FullActorData
import com.example.maverickshows.app.actor.domain.GetAllActorData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val getAllActorData: GetAllActorData
): ViewModel() {
    private val _uiState = MutableStateFlow<ActorUiState>(ActorUiState.Loading)
    val uiState: StateFlow<ActorUiState> = _uiState.asStateFlow()

    fun getActorDetails(id: String) {
        viewModelScope.launch {
            try {
                val data: FullActorData = getAllActorData(id)
                _uiState.value = ActorUiState.Success(
                    data = data.actorData,
                    img = data.actorImages,
                    genre = data.genre,
                    filmographyData = data.actorFilmography
                )
            } catch (e: Exception) {
                _uiState.value = ActorUiState.Error(e.message.toString())
            }
        }
    }

    fun getStringGenre(genreIds: List<Int>): List<String> {
        val stringGenres = mutableListOf<String>()
        if (_uiState.value is ActorUiState.Success) {
            val currentState = _uiState.value as ActorUiState.Success
            for (genreId in genreIds) {
                for (genre in currentState.genre) {
                    if (genre.id == genreId) {
                        stringGenres.add(genre.name)
                        break
                    } else {
                        continue
                    }
                }
            }
        }
        return stringGenres
    }
}