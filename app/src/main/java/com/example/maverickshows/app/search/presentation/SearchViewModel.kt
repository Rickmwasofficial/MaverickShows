package com.example.maverickshows.app.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.presentation.HomeUIState
import com.example.maverickshows.app.search.data.SearchRepImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepImpl: SearchRepImpl
): ViewModel() {
    private var _uiState = MutableStateFlow<SearchUIState>(
        SearchUIState.Idle
    )
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()
    private var _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    fun updateQuery(q: String) {
//        _uiState.value = SearchUIState.Loading
        _query.value = q.toLowerCase()
        getSearchResults()
    }

    fun getSearchResults() {
        viewModelScope.launch {
            try {
                val results: List<HomeData> = searchRepImpl.getSearchResults(query.value)
                val genres = searchRepImpl.getAllGenres()
                _uiState.value = SearchUIState.Success(
                    data = results,
                    genres = genres
                )
            } catch (e: Exception) {
                _uiState.value = SearchUIState.Error(e.message.toString())
            }
        }
    }

    fun getStringGenre(genreIds: List<Int>): List<String> {
        val stringGenres = mutableListOf<String>()
        if (_uiState.value is SearchUIState.Success) {
            val currentState = _uiState.value as SearchUIState.Success
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