package com.example.maverickshows.app.home.presentation

import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                _uiState.value = HomeUIState.Success(
                    allPopular = allPopular,
                    allTrending = allTrending,
                    allTopRated = allTopRated
                )
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message.toString())
            }
        }
    }
}