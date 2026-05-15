package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.domain.model.Language
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val languageCode: Flow<String>
    val isDarkMode: Flow<Boolean>
    val fontScale: Flow<Float>
    val isOnboardingCompleted: Flow<Boolean>
    val isProfileCompleted: Flow<Boolean>
    val currentShilpiId: Flow<String?>

    suspend fun setLanguage(language: Language)
    suspend fun setDarkMode(isDark: Boolean)
    suspend fun setFontScale(scale: Float)
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setProfileCompleted(completed: Boolean)
    suspend fun setCurrentShilpiId(id: String)
    suspend fun clearCache()
    suspend fun exportData(): String
}
