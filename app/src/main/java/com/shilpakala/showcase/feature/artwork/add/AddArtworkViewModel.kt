package com.shilpakala.showcase.feature.artwork.add

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.ImageCompressor
import com.shilpakala.showcase.domain.model.ArtworkStatus
import com.shilpakala.showcase.domain.usecase.artwork.AddArtworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class AddArtworkUiState(
    val shilpiId: String = "", val title: String = "", val material: String = "",
    val dimensions: String = "", val priceRange: String = "", val description: String = "",
    val status: ArtworkStatus = ArtworkStatus.AVAILABLE, val imageUris: List<String> = emptyList(),
    val isLoading: Boolean = false, val error: String? = null, val isSaved: Boolean = false
)

@HiltViewModel
class AddArtworkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addArtworkUseCase: AddArtworkUseCase,
    private val imageCompressor: ImageCompressor
) : ViewModel() {
    private val shilpiId: String = savedStateHandle["shilpiId"] ?: ""
    private val _uiState = MutableStateFlow(AddArtworkUiState(shilpiId = shilpiId))
    val uiState: StateFlow<AddArtworkUiState> = _uiState.asStateFlow()

    fun updateTitle(v: String) { _uiState.update { it.copy(title = v) } }
    fun updateMaterial(v: String) { _uiState.update { it.copy(material = v) } }
    fun updateDimensions(v: String) { _uiState.update { it.copy(dimensions = v) } }
    fun updatePriceRange(v: String) { _uiState.update { it.copy(priceRange = v) } }
    fun updateDescription(v: String) { _uiState.update { it.copy(description = v) } }
    fun updateStatus(v: ArtworkStatus) { _uiState.update { it.copy(status = v) } }

    fun addImages(uris: List<Uri>, cacheDir: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val dir = File(cacheDir, "artwork_images")
            val compressed = uris.mapNotNull { uri ->
                imageCompressor.compressImage(uri, dir, "art_${System.currentTimeMillis()}_${uris.indexOf(uri)}.jpg")?.absolutePath
            }
            val current = _uiState.value.imageUris
            _uiState.update { it.copy(imageUris = (current + compressed).take(30), isLoading = false) }
        }
    }

    fun removeImage(index: Int) {
        _uiState.update { it.copy(imageUris = it.imageUris.toMutableList().apply { removeAt(index) }) }
    }

    fun save() {
        viewModelScope.launch {
            val s = _uiState.value
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = addArtworkUseCase(s.shilpiId, s.title, s.material, s.dimensions, s.priceRange, s.status, s.imageUris, s.description)
            when (result) {
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, isSaved = true) }
                is Resource.Error -> _uiState.update { it.copy(isLoading = false, error = result.message) }
                is Resource.Loading -> {}
            }
        }
    }
}
