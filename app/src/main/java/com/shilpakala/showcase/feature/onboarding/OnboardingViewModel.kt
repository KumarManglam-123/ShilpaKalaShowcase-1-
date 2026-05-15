package com.shilpakala.showcase.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.domain.model.Language
import com.shilpakala.showcase.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val currentPage: Int = 0,
    val selectedLanguage: Language = Language.ENGLISH,
    val isLanguageSelected: Boolean = false,
    val totalPages: Int = 3
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun selectLanguage(language: Language) {
        _uiState.update { it.copy(selectedLanguage = language, isLanguageSelected = true) }
        viewModelScope.launch { settingsRepository.setLanguage(language) }
    }

    fun nextPage() {
        _uiState.update { it.copy(currentPage = (it.currentPage + 1).coerceAtMost(it.totalPages - 1)) }
    }

    fun setPage(page: Int) {
        _uiState.update { it.copy(currentPage = page) }
    }

    fun completeOnboarding() {
        viewModelScope.launch { settingsRepository.setOnboardingCompleted(true) }
    }
}
