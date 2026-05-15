package com.shilpakala.showcase.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.usecase.artwork.GetArtworksUseCase
import com.shilpakala.showcase.domain.usecase.shilpi.GetShilpiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val currentShilpi: Shilpi? = null,
    val featuredArtisan: Shilpi? = null,
    val recentArtworks: List<Artwork> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getShilpiUseCase: GetShilpiUseCase,
    private val getArtworksUseCase: GetArtworksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            launch {
                getShilpiUseCase.observeCurrent().collect { shilpi ->
                    _uiState.update { it.copy(currentShilpi = shilpi, isLoading = false) }
                }
            }
            launch {
                getArtworksUseCase.observeRecent(10).collect { artworks ->
                    _uiState.update { it.copy(recentArtworks = artworks) }
                }
            }
            launch {
                val featured = getShilpiUseCase.getRandomShilpi()
                _uiState.update { it.copy(featuredArtisan = featured ?: it.currentShilpi) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            loadData()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
