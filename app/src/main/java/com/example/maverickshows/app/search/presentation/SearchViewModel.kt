package com.example.maverickshows.app.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.core.data.RecentSearchEntity
import com.example.maverickshows.app.search.data.SearchRepImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
        observeRecentSearches()
    }

    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()
    private var _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var searchJob: Job? = null

    // Single function to observe recent searches and update state accordingly
    private fun observeRecentSearches() {
        viewModelScope.launch {
            searchRepImpl.getAllSearchedItemsStream()
                .collectLatest { recentSearches ->
                    val searches = searchRepImpl.getSavedShows(recentSearches)

                    // Update state based on current state type
                    when (val currentState = _uiState.value) {
                        is SearchUIState.Idle -> {
                            _uiState.value = currentState.copy(recentSearches = searches)
                        }
                        is SearchUIState.Success -> {
                            _uiState.value = currentState.copy(recentSearches = searches)
                        }
                        // Don't update Loading or Error states with recent searches
                        is SearchUIState.Loading, is SearchUIState.Error -> {
                            // Keep current state unchanged
                        }
                    }
                }
        }
    }

    fun resetToIdle() {
        viewModelScope.launch {
            val recentSearchEntities = searchRepImpl.getAllSearchedItemsStream().first()
            val searches = searchRepImpl.getSavedShows(recentSearchEntities)
            _uiState.value = SearchUIState.Idle(recentSearches = searches)
        }
    }

    fun updateQuery(q: String) {
        val normalizedQuery = q.toLowerCase()
        _query.value = normalizedQuery

        searchJob?.cancel()

        if (normalizedQuery.isEmpty()) {
            resetToIdle()
            return
        }

        searchJob = viewModelScope.launch {
            delay(300)
            if (_query.value == normalizedQuery) {
                getSearchResults()
            }
        }
    }

    fun saveItem(item: RecentSearchEntity) {
        viewModelScope.launch {
            searchRepImpl.insertSearchedItem(item)
        }
    }

    fun deleteItem(item: RecentSearchEntity) {
        val currentState = _uiState.value
        when (currentState) {
            is SearchUIState.Idle -> {
                _uiState.value = currentState.copy(
                    recentSearches = currentState.recentSearches.filterNot { it.id.toString() == item.id }
                )
            }
            is SearchUIState.Success -> {
                _uiState.value = currentState.copy(
                    recentSearches = currentState.recentSearches.filterNot { it.id.toString() == item.id }
                )
            }
        }

        // Then update database
        viewModelScope.launch {
            try {
                searchRepImpl.deleteItem(item)
            } catch (e: Exception) {
                // If database operation fails, revert the UI change
                observeRecentSearches() // This will reload from database
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            searchRepImpl.deleteAllItems()
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

        // Safe state checking instead of unsafe casting
        when (val currentState = _uiState.value) {
            is SearchUIState.Success -> {
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