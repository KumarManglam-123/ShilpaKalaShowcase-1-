package com.shilpakala.showcase.domain.usecase.heritage

import com.shilpakala.showcase.domain.model.Heritage
import com.shilpakala.showcase.domain.repository.HeritageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeritageStoriesUseCase @Inject constructor(private val repository: HeritageRepository) {
    fun observeAll(): Flow<List<Heritage>> = repository.observeAllHeritage()
    fun observeByLanguage(language: String): Flow<List<Heritage>> = repository.observeHeritageByLanguage(language)
    suspend fun refresh(language: String) = repository.refreshHeritage(language)
}
