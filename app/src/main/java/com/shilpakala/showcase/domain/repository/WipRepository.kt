package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.WipStage
import kotlinx.coroutines.flow.Flow

interface WipRepository {
    fun observeStagesByArtwork(artworkId: String): Flow<List<WipStage>>
    suspend fun addStage(stage: WipStage): Resource<WipStage>
    suspend fun updateStage(stage: WipStage): Resource<Unit>
    suspend fun deleteStage(stage: WipStage): Resource<Unit>
    suspend fun getStageCount(artworkId: String): Int
}
