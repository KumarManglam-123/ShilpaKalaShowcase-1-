package com.shilpakala.showcase.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shilpakala.showcase.domain.model.Language
import com.shilpakala.showcase.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val languageCode: String = "en", val isDarkMode: Boolean = false,
    val fontScale: Float = 1.0f, val appVersion: String = "1.0.0",
    val cacheCleared: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(settingsRepository.languageCode, settingsRepository.isDarkMode, settingsRepository.fontScale) { lang, dark, font ->
                _uiState.value.copy(languageCode = lang, isDarkMode = dark, fontScale = font)
            }.collect { state -> _uiState.value = state }
        }
    }

    fun setLanguage(language: Language) { viewModelScope.launch { settingsRepository.setLanguage(language) } }
    fun setDarkMode(isDark: Boolean) { viewModelScope.launch { settingsRepository.setDarkMode(isDark) } }
    fun setFontScale(scale: Float) { viewModelScope.launch { settingsRepository.setFontScale(scale) } }
    fun clearCache() {
        viewModelScope.launch {
            settingsRepository.clearCache()
            _uiState.update { it.copy(cacheCleared = true) }
        }
    }
    fun exportData() { viewModelScope.launch { settingsRepository.exportData() } }
}
