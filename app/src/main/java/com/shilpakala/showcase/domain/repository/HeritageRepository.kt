package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Heritage
import kotlinx.coroutines.flow.Flow

interface HeritageRepository {
    fun observeAllHeritage(): Flow<List<Heritage>>
    fun observeHeritageByLanguage(language: String): Flow<List<Heritage>>
    suspend fun getHeritageById(id: String): Resource<Heritage>
    suspend fun generateNarrative(style: String, language: String): Resource<Heritage>
    suspend fun refreshHeritage(language: String): Resource<List<Heritage>>
    suspend fun clearCache()
}
