package com.shilpakala.showcase.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.domain.model.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = AppConstants.DATASTORE_NAME
)

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    companion object Keys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val PROFILE_COMPLETED = booleanPreferencesKey("profile_completed")
        val LANGUAGE_CODE = stringPreferencesKey("language_code")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val FONT_SCALE = floatPreferencesKey("font_scale")
        val CURRENT_SHILPI_ID = stringPreferencesKey("current_shilpi_id")
        val LAST_SYNC_TIMESTAMP = stringPreferencesKey("last_sync_timestamp")
    }

    val isOnboardingCompleted: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[ONBOARDING_COMPLETED] ?: false }

    val isProfileCompleted: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[PROFILE_COMPLETED] ?: false }

    val languageCode: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[LANGUAGE_CODE] ?: Language.ENGLISH.code }

    val isDarkMode: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[IS_DARK_MODE] ?: false }

    val fontScale: Flow<Float> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[FONT_SCALE] ?: 1.0f }

    val currentShilpiId: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw exception
        }
        .map { preferences -> preferences[CURRENT_SHILPI_ID] }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setProfileCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PROFILE_COMPLETED] = completed
        }
    }

    suspend fun setLanguageCode(code: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_CODE] = code
        }
    }

    suspend fun setDarkMode(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    suspend fun setFontScale(scale: Float) {
        dataStore.edit { preferences ->
            preferences[FONT_SCALE] = scale.coerceIn(0.8f, 2.0f)
        }
    }

    suspend fun setCurrentShilpiId(id: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_SHILPI_ID] = id
        }
    }

    suspend fun setLastSyncTimestamp(timestamp: String) {
        dataStore.edit { preferences ->
            preferences[LAST_SYNC_TIMESTAMP] = timestamp
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}
