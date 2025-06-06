package com.example.maverickshows.app.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.details.domain.ShowData
import com.example.maverickshows.app.details.domain.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val showDetail: ShowDetail
): ViewModel() {
    private var _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun getDetails(id: String, type: String) {
        viewModelScope.launch {
            try {
                val data: ShowData = showDetail(id, type)
                _uiState.value = DetailUiState.Success(
                    data = data.details,
                    imgData = data.images,
                    credits = data.credits,
                    recommendations = data.recommendations,
                    genres = data.genres
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message.toString())
            }
        }
    }

    fun getStringGenre(genreIds: List<Int>): List<String> {
        val stringGenres = mutableListOf<String>()
        if (_uiState.value is DetailUiState.Success) {
            val currentState = _uiState.value as DetailUiState.Success
            for (genreId in genreIds) {
                for (genre in currentState.genres) {
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