package com.shilpakala.showcase.data.repository

import android.content.Context
import com.shilpakala.showcase.data.local.datastore.PreferencesManager
import com.shilpakala.showcase.domain.model.Language
import com.shilpakala.showcase.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    @ApplicationContext private val context: Context
) : SettingsRepository {
    override val languageCode: Flow<String> = preferencesManager.languageCode
    override val isDarkMode: Flow<Boolean> = preferencesManager.isDarkMode
    override val fontScale: Flow<Float> = preferencesManager.fontScale
    override val isOnboardingCompleted: Flow<Boolean> = preferencesManager.isOnboardingCompleted
    override val isProfileCompleted: Flow<Boolean> = preferencesManager.isProfileCompleted
    override val currentShilpiId: Flow<String?> = preferencesManager.currentShilpiId

    override suspend fun setLanguage(language: Language) = preferencesManager.setLanguageCode(language.code)
    override suspend fun setDarkMode(isDark: Boolean) = preferencesManager.setDarkMode(isDark)
    override suspend fun setFontScale(scale: Float) = preferencesManager.setFontScale(scale)
    override suspend fun setOnboardingCompleted(completed: Boolean) = preferencesManager.setOnboardingCompleted(completed)
    override suspend fun setProfileCompleted(completed: Boolean) = preferencesManager.setProfileCompleted(completed)
    override suspend fun setCurrentShilpiId(id: String) = preferencesManager.setCurrentShilpiId(id)

    override suspend fun clearCache() {
        context.cacheDir.deleteRecursively()
        context.cacheDir.mkdirs()
    }

    override suspend fun exportData(): String {
        val exportDir = File(context.getExternalFilesDir(null), "export")
        exportDir.mkdirs()
        return exportDir.absolutePath
    }
}
