package com.example.maverickshows.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.home.domain.GetHomeContentUseCase
import com.example.maverickshows.app.home.domain.HomeContent
import com.example.maverickshows.app.home.domain.RefreshUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeContentUseCase: GetHomeContentUseCase,
    private val refreshUseCase: RefreshUseCase
): ViewModel() {
    private var _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    var uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()
    private var _contentState = MutableStateFlow<ContentUIState>(ContentUIState.All)
    var contentState: StateFlow<ContentUIState> = _contentState.asStateFlow()

    init {
        loadAllData(contentState.value, page = 1)
    }

    fun loadAllData(state: ContentUIState, page: Int) {
        viewModelScope.launch {
            try {
                val result: HomeContent = getHomeContentUseCase(state, page)
                _uiState.value = HomeUIState.Success(
                    allPopular = result.popular,
                    allTrending = result.trending,
                    allTopRated = result.topRated,
                    genres = result.genres,
                    nextPage = page + 1,
                    upcomingMovies = result.upcoming,
                    airingTv = result.airing,
                    onAirTv = result.onAir,
                    nowPlaying = result.nowPlaying,
                    isRefreshing = false
                )
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message.toString())
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val currentState = _uiState.value as HomeUIState.Success
            _uiState.value = currentState.copy(
                isRefreshing = true
            )
            try {
                val result = refreshUseCase(_contentState.value)
                _uiState.value = HomeUIState.Success(
                    allPopular = result.popular,
                    allTrending = result.trending,
                    allTopRated = result.topRated,
                    genres = result.genres,
                    nextPage = currentState.nextPage + 1,
                    upcomingMovies = result.upcoming,
                    airingTv = result.airing,
                    onAirTv = result.onAir,
                    nowPlaying = result.nowPlaying,
                    isRefreshing = false
                )
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message.toString())
            }
        }
    }

    fun getStringGenre(genreIds: List<Int>): List<String> {
        val stringGenres = mutableListOf<String>()
        if (_uiState.value is HomeUIState.Success) {
            val currentState = _uiState.value as HomeUIState.Success
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

    fun setContentState(state: String) {
        val currentState = _uiState.value as HomeUIState.Success
        _contentState.value = when (state) {
            "All" -> ContentUIState.All
            "Movies" -> ContentUIState.Movie
            "Series" -> ContentUIState.Series
            else -> ContentUIState.All
        }
        loadAllData(contentState.value, currentState.nextPage - 1)
    }
}