package com.shilpakala.showcase.feature.wip

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.ImageCompressor
import com.shilpakala.showcase.domain.model.WipStage
import com.shilpakala.showcase.domain.model.WipStageName
import com.shilpakala.showcase.domain.usecase.wip.AddWipStageUseCase
import com.shilpakala.showcase.domain.usecase.wip.GetWipTimelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class WipUiState(
    val artworkId: String = "", val stages: List<WipStage> = emptyList(),
    val isLoading: Boolean = true, val error: String? = null,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class WipViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWipTimelineUseCase: GetWipTimelineUseCase,
    private val addWipStageUseCase: AddWipStageUseCase,
    private val imageCompressor: ImageCompressor
) : ViewModel() {
    private val artworkId: String = savedStateHandle["artworkId"] ?: ""
    private val _uiState = MutableStateFlow(WipUiState(artworkId = artworkId))
    val uiState: StateFlow<WipUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getWipTimelineUseCase(artworkId).collect { stages ->
                _uiState.update { it.copy(stages = stages, isLoading = false) }
            }
        }
    }

    fun showAddDialog() { _uiState.update { it.copy(showAddDialog = true) } }
    fun hideAddDialog() { _uiState.update { it.copy(showAddDialog = false) } }

    fun addStage(stageName: WipStageName, photoUri: Uri?, caption: String, cacheDir: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            var compressedPath: String? = null
            if (photoUri != null) {
                val dir = File(cacheDir, "wip_images")
                val compressed = imageCompressor.compressImage(photoUri, dir, "wip_${System.currentTimeMillis()}.jpg")
                compressedPath = compressed?.absolutePath
            }
            when (val result = addWipStageUseCase(artworkId, stageName, compressedPath, caption)) {
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, showAddDialog = false) }
                is Resource.Error -> _uiState.update { it.copy(isLoading = false, error = result.message) }
                is Resource.Loading -> {}
            }
        }
    }
}
