package com.shilpakala.showcase.feature.artwork.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.usecase.artwork.GetArtworksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArtworkListUiState(val artworks: List<Artwork> = emptyList(), val isLoading: Boolean = true, val shilpiId: String = "")

@HiltViewModel
class ArtworkListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtworksUseCase: GetArtworksUseCase
) : ViewModel() {
    private val shilpiId: String = savedStateHandle["shilpiId"] ?: ""
    private val _uiState = MutableStateFlow(ArtworkListUiState(shilpiId = shilpiId))
    val uiState: StateFlow<ArtworkListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getArtworksUseCase.observeByShilpi(shilpiId).collect { artworks ->
                _uiState.update { it.copy(artworks = artworks, isLoading = false) }
            }
        }
    }
}
