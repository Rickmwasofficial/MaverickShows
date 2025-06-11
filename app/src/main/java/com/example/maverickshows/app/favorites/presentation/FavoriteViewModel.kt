package com.example.maverickshows.app.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.favorites.data.FavoritesRepImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepImpl: FavoritesRepImpl
): ViewModel() {
    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }
    private fun observeFavorites() {
        viewModelScope.launch {
            try {
                val genres = favoritesRepImpl.getAllGenres()
                favoritesRepImpl.getAllLikedItemsStream()
                    .collectLatest { recentSearches ->
                        val searches = favoritesRepImpl.getLikedShows(recentSearches)

                        _uiState.value = FavoriteUiState.Success(
                            genres,
                            searches
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = FavoriteUiState.Error(e.message.toString())
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            favoritesRepImpl.deleteAllItems()
        }
    }

    fun getStringGenre(genreIds: List<Int>): List<String> {
        val stringGenres = mutableListOf<String>()

        // Safe state checking instead of unsafe casting
        when (val currentState = _uiState.value) {
            is FavoriteUiState.Success -> {
                for (genreId in genreIds) {
                    val genre = currentState.genres.find { it.id == genreId }
                    genre?.let { stringGenres.add(it.name) }
                }
            }
            else -> {
                // Return empty list if not in Success state
            }
        }

        return stringGenres
    }
}