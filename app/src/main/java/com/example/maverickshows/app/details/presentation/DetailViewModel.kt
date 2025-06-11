package com.example.maverickshows.app.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.core.data.FavoritesEntity
import com.example.maverickshows.app.details.data.DetailDataRepImpl
import com.example.maverickshows.app.details.domain.LikeUseCase
import com.example.maverickshows.app.details.domain.ShowData
import com.example.maverickshows.app.details.domain.ShowDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val showDetail: ShowDetail,
    private val detailDataRepImpl: DetailDataRepImpl,
    private val likeUseCase: LikeUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _likedItems = MutableStateFlow<Set<String>>(emptySet())
    val likedItems: StateFlow<Set<String>> = _likedItems.asStateFlow()

    init {
        observeLikedItems()
    }

    private fun observeLikedItems() {
        viewModelScope.launch {
            detailDataRepImpl.getAllLikedItemsStream()
                .collectLatest { favorites ->
                    _likedItems.value = favorites.map { it.id }.toSet()
                }
        }
    }

    fun getDetails(id: String, type: String) {
        viewModelScope.launch {
            try {
                val data: ShowData = showDetail(id, type)
                _uiState.value = DetailUiState.Success(
                    data = data.details,
                    imgData = data.images,
                    credits = data.credits,
                    recommendations = data.recommendations,
                    genres = data.genres,
                    trailers = data.trailers
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message.toString())
            }
        }
    }

    fun likeItem(state: Boolean, item: FavoritesEntity) {
        viewModelScope.launch {
            likeUseCase(state, item)
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