package com.example.maverickshows.app.actor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maverickshows.app.actor.data.ActorDataRepImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorDataRepImpl: ActorDataRepImpl
): ViewModel() {
    private val _uiState = MutableStateFlow<ActorUiState>(ActorUiState.Loading)
    val uiState: StateFlow<ActorUiState> = _uiState.asStateFlow()

    fun getActorDetails(id: String) {
        viewModelScope.launch {
            try {
                val details = actorDataRepImpl.getActorData(id)
                val images = actorDataRepImpl.getActorImages(id)
                _uiState.value = ActorUiState.Success(
                    data = details,
                    img = images
                )
            } catch (e: Exception) {
                _uiState.value = ActorUiState.Error(e.message.toString())
            }
        }
    }
}