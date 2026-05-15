package com.shilpakala.showcase.domain.usecase.settings

import com.shilpakala.showcase.domain.model.Language
import com.shilpakala.showcase.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<String> = repository.languageCode
}

class SetLanguageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(language: Language) = repository.setLanguage(language)
}
