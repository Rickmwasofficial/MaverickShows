package com.example.maverickshows.app.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        loadAllData()
    }

    fun loadAllData() {
        viewModelScope.launch {
            try {
                val allTrending = homeDataRepositoryImpl.getAllTrending()
                val allPopular = homeDataRepositoryImpl.getAllPopular()
                val allTopRated = homeDataRepositoryImpl.getAllTopRated()
                val genres = homeDataRepositoryImpl.getAllGenres()
                _uiState.value = HomeUIState.Success(
                    allPopular = allPopular,
                    allTrending = allTrending,
                    allTopRated = allTopRated,
                    genres = genres
                )
                setHeroImage()
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message.toString())
            }
        }
    }
    private fun setHeroImage() {
        viewModelScope.launch {
            if (_uiState.value is HomeUIState.Success) { // Check initial state
                while (true) {
                    delay(12000)
                    if (_uiState.value is HomeUIState.Success) { // Re-check inside loop
                        val currentState = _uiState.value as HomeUIState.Success
                        val nextHero = (currentState.hero + 1) % currentState.allTrending.size
                        _uiState.value = currentState.copy(hero = nextHero)
                    } else {
                        break // Exit loop if state changes
                    }
                }
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
}