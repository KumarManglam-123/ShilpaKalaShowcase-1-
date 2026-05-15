package com.shilpakala.showcase.feature.heritage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Heritage
import com.shilpakala.showcase.domain.usecase.heritage.GetHeritageStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HeritageUiState(
    val stories: List<Heritage> = emptyList(),
    val selectedStory: Heritage? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class HeritageViewModel @Inject constructor(
    private val getHeritageStoriesUseCase: GetHeritageStoriesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HeritageUiState())
    val uiState: StateFlow<HeritageUiState> = _uiState.asStateFlow()

    init { loadHeritage() }

    private fun loadHeritage() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getHeritageStoriesUseCase.refresh("en")
            getHeritageStoriesUseCase.observeByLanguage("en").collect { stories ->
                _uiState.update { it.copy(stories = stories, isLoading = false) }
            }
        }
    }

    fun selectStory(id: String) {
        val story = _uiState.value.stories.find { it.id == id }
        _uiState.update { it.copy(selectedStory = story) }
    }
}
