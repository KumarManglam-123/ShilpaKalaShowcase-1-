package com.shilpakala.showcase.domain.usecase.heritage

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Heritage
import com.shilpakala.showcase.domain.repository.HeritageRepository
import javax.inject.Inject

class GenerateHeritageNarrativeUseCase @Inject constructor(
    private val repository: HeritageRepository
) {
    suspend operator fun invoke(style: String, language: String): Resource<Heritage> {
        return repository.generateNarrative(style, language)
    }
}
