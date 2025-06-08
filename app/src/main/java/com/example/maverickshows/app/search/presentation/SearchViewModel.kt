package com.example.maverickshows.app.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.maverickshows.app.core.data.RecentSearchEntity
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.presentation.HomeUIState
import com.example.maverickshows.app.search.data.SearchRepImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepImpl: SearchRepImpl
): ViewModel() {
    private var _uiState = MutableStateFlow<SearchUIState>(
        SearchUIState.Idle(
            listOf()
        )
    )

    init {
        getRecentSearches()
        // Fixed: Remove the infinite while loop and properly observe recent searches
        observeRecentSearchesForSuccessState()
    }

    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()
    private var _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private fun observeRecentSearchesForSuccessState() {
        viewModelScope.launch {
            searchRepImpl.getAllSearchedItemsStream()
                .collectLatest { recentSearches ->
                    if (_uiState.value is SearchUIState.Success) {
                        val currentState = _uiState.value as SearchUIState.Success
                        val searches = searchRepImpl.getSavedShows(recentSearches)

                        _uiState.value = currentState.copy(
                            recentSearches = searches
                        )
                    }
                }
        }
    }

    fun getRecentSearches() {
        viewModelScope.launch {
            searchRepImpl.getAllSearchedItemsStream()
                .collectLatest { recentSearches ->
                    val searches = searchRepImpl.getSavedShows(recentSearches)

                    _uiState.value = SearchUIState.Idle(
                        recentSearches = searches
                    )
                }
        }
    }

    fun updateQuery(q: String) {
//        _uiState.value = SearchUIState.Loading
        _query.value = q.toLowerCase()
        getSearchResults()
    }
    fun saveItem(item: RecentSearchEntity) {
        viewModelScope.launch {
            searchRepImpl.insertSearchedItem(item)
        }
    }

    fun deleteItem(item: RecentSearchEntity) {
        viewModelScope.launch {
            searchRepImpl.deleteItem(item)
        }
    }

    fun getSearchResults() {
        viewModelScope.launch {
            try {
                val results = searchRepImpl.getSearchResults(query.value)
                val genres = searchRepImpl.getAllGenres()
                val recentSearchEntities = searchRepImpl.getAllSearchedItemsStream().first()
                val searches = searchRepImpl.getSavedShows(recentSearchEntities)

                _uiState.value = SearchUIState.Success(
                    data = results,
                    genres = genres,
                    recentSearches = searches
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