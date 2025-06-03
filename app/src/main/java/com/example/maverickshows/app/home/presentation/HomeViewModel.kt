package com.example.maverickshows.app.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.Home
import com.example.maverickshows.app.home.domain.HomeData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataRepositoryImpl: HomeDataRepositoryImpl
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
                lateinit var trending: List<HomeData>
                lateinit var popular: List<HomeData>
                lateinit var topRated: List<HomeData>

                if (state is ContentUIState.All) {
                    trending = homeDataRepositoryImpl.getAllTrending(page = page.toString())
                    popular = homeDataRepositoryImpl.getAllPopular(page = page.toString())
                    topRated = homeDataRepositoryImpl.getAllTopRated(page = page.toString())
                } else if (state is ContentUIState.Movie) {
                    trending = homeDataRepositoryImpl.getTrendingMovies(page = page.toString())
                    popular = homeDataRepositoryImpl.getPopularMovies(page = page.toString())
                    topRated = homeDataRepositoryImpl.getTopRatedMovies(page = page.toString())
                } else if (state is ContentUIState.Series) {
                    trending = homeDataRepositoryImpl.getTrendingTv(page = page.toString())
                    popular = homeDataRepositoryImpl.getPopularTv(page = page.toString())
                    topRated = homeDataRepositoryImpl.getTopRatedTv(page = page.toString())
                }

                val genres = homeDataRepositoryImpl.getAllGenres()
                val upcoming = homeDataRepositoryImpl.getUpcomingMovies(page = page.toString())
                val airing = homeDataRepositoryImpl.getAiringTv(page = page.toString())
                val onair = homeDataRepositoryImpl.getOnAirTv(page = page.toString())
                val nowPlaying = homeDataRepositoryImpl.getNowPlayingMovies(page = page.toString())

                _uiState.value = HomeUIState.Success(
                    allPopular = popular,
                    allTrending = trending,
                    allTopRated = topRated,
                    genres = genres,
                    nextPage = page + 1,
                    upcomingMovies = upcoming,
                    airingTv = airing,
                    onAirTv = onair,
                    nowPlaying = nowPlaying,
                    isRefreshing = false
                )
                setHeroImage()
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message.toString())
            }
        }
    }
    private var heroImageLoopJob: Job? = null
    private fun setHeroImage() {
        heroImageLoopJob?.cancel()
        heroImageLoopJob = viewModelScope.launch {
            val initialState = _uiState.value
            if (initialState is HomeUIState.Success) {
                while (true) {
                    delay(12000)
                    val currentState = _uiState.value
                    if (currentState is HomeUIState.Success && currentState.allTrending.isNotEmpty()) {
                        val nextHero = (currentState.hero + 1) % currentState.allTrending.size
                        _uiState.value = currentState.copy(hero = nextHero)
                    } else {
                        break
                    }
                }
            }
        }
    }
//
//    fun getNextPage(page: Int) {
//        loadAllData(contentState.value, page)
//    }

    fun refresh() {
        val pages = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        val currentState = _uiState.value as HomeUIState.Success
        _uiState.value = currentState.copy(
            isRefreshing = true
        )
        loadAllData(contentState.value, pages.random())
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