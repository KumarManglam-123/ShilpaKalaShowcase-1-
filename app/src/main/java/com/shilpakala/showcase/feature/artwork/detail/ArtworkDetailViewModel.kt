package com.shilpakala.showcase.feature.artwork.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.usecase.artwork.GetArtworksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArtworkDetailUiState(val artwork: Artwork? = null, val isLoading: Boolean = true)

@HiltViewModel
class ArtworkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtworksUseCase: GetArtworksUseCase
) : ViewModel() {
    private val artworkId: String = savedStateHandle["artworkId"] ?: ""
    private val _uiState = MutableStateFlow(ArtworkDetailUiState())
    val uiState: StateFlow<ArtworkDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getArtworksUseCase.observeById(artworkId).collect { artwork ->
                _uiState.update { it.copy(artwork = artwork, isLoading = false) }
            }
        }
    }
}
