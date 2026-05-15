package com.shilpakala.showcase.feature.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.ImageCompressor
import com.shilpakala.showcase.domain.repository.SettingsRepository
import com.shilpakala.showcase.domain.usecase.shilpi.CreateShilpiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class ProfileWizardState(
    val currentStep: Int = 0,
    val totalSteps: Int = 4,
    val name: String = "",
    val village: String = "",
    val district: String = "",
    val specialisation: String = "",
    val profilePhotoUri: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isComplete: Boolean = false,
    val nameError: String? = null,
    val locationError: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val createShilpiUseCase: CreateShilpiUseCase,
    private val settingsRepository: SettingsRepository,
    private val imageCompressor: ImageCompressor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileWizardState())
    val uiState: StateFlow<ProfileWizardState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun updateVillage(village: String) {
        _uiState.update { it.copy(village = village, locationError = null) }
    }

    fun updateDistrict(district: String) {
        _uiState.update { it.copy(district = district, locationError = null) }
    }

    fun updateSpecialisation(specialisation: String) {
        _uiState.update { it.copy(specialisation = specialisation) }
    }

    fun updateProfilePhoto(uri: Uri, cacheDir: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val dir = File(cacheDir, "profile_photos")
            val compressed = imageCompressor.compressImage(
                uri = uri, directory = dir,
                fileName = "profile_${System.currentTimeMillis()}.jpg",
                maxDimension = 1024, quality = 90
            )
            _uiState.update {
                it.copy(
                    profilePhotoUri = compressed?.absolutePath ?: uri.toString(),
                    isLoading = false
                )
            }
        }
    }

    fun nextStep(): Boolean {
        val state = _uiState.value
        when (state.currentStep) {
            0 -> {
                if (state.name.isBlank()) {
                    _uiState.update { it.copy(nameError = "Name is required") }
                    return false
                }
            }
            1 -> {
                if (state.village.isBlank() || state.district.isBlank()) {
                    _uiState.update { it.copy(locationError = "Village and district are required") }
                    return false
                }
            }
            2 -> {
                if (state.specialisation.isBlank()) return false
            }
        }
        if (state.currentStep < state.totalSteps - 1) {
            _uiState.update { it.copy(currentStep = state.currentStep + 1, error = null) }
        }
        return true
    }

    fun previousStep() {
        val state = _uiState.value
        if (state.currentStep > 0) {
            _uiState.update { it.copy(currentStep = state.currentStep - 1, error = null) }
        }
    }

    fun finishProfile() {
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = createShilpiUseCase(
                name = state.name, village = state.village, district = state.district,
                specialisation = state.specialisation, profilePhotoUri = state.profilePhotoUri
            )

            when (result) {
                is Resource.Success -> {
                    settingsRepository.setCurrentShilpiId(result.data.id)
                    settingsRepository.setProfileCompleted(true)
                    _uiState.update { it.copy(isLoading = false, isComplete = true) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun clearError() { _uiState.update { it.copy(error = null) } }
}
