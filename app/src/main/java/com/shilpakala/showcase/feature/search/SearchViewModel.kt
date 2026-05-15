package com.shilpakala.showcase.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.SearchFilter
import com.shilpakala.showcase.domain.usecase.search.SearchArtworksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "", val filter: SearchFilter = SearchFilter(),
    val results: List<Artwork> = emptyList(), val isLoading: Boolean = false,
    val materials: List<String> = emptyList(), val selectedMaterial: String? = null,
    val hasSearched: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArtworksUseCase: SearchArtworksUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            val materials = searchArtworksUseCase.getMaterials()
            _uiState.update { it.copy(materials = materials) }
        }

        @OptIn(FlowPreview::class)
        viewModelScope.launch {
            _searchQuery.debounce(AppConstants.SEARCH_DEBOUNCE_MS)
                .distinctUntilChanged()
                .filter { it.isNotBlank() || _uiState.value.filter.hasActiveFilters }
                .flatMapLatest { query ->
                    _uiState.update { it.copy(isLoading = true) }
                    val filter = _uiState.value.filter.copy(query = query)
                    searchArtworksUseCase(filter)
                }
                .collect { results ->
                    _uiState.update { it.copy(results = results, isLoading = false, hasSearched = true) }
                }
        }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        _searchQuery.value = query
    }

    fun selectMaterial(material: String?) {
        _uiState.update { it.copy(selectedMaterial = material, filter = it.filter.copy(material = material)) }
        _searchQuery.value = _uiState.value.query // trigger re-search
    }
}
