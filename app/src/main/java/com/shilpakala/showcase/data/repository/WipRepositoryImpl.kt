package com.shilpakala.showcase.data.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.resource.safeCall
import com.shilpakala.showcase.data.local.db.dao.WipDao
import com.shilpakala.showcase.data.mapper.toDomain
import com.shilpakala.showcase.data.mapper.toEntity
import com.shilpakala.showcase.domain.model.WipStage
import com.shilpakala.showcase.domain.repository.WipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WipRepositoryImpl @Inject constructor(
    private val wipDao: WipDao
) : WipRepository {

    override fun observeStagesByArtwork(artworkId: String): Flow<List<WipStage>> {
        return wipDao.observeStagesByArtwork(artworkId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addStage(stage: WipStage): Resource<WipStage> = safeCall {
        // Prevent duplicate stages
        val existing = wipDao.getStageByName(stage.artworkId, stage.stageName.name)
        if (existing != null) {
            throw IllegalArgumentException("Stage ${stage.stageName.displayName} already exists for this artwork")
        }
        wipDao.insertStage(stage.toEntity())
        stage
    }

    override suspend fun updateStage(stage: WipStage): Resource<Unit> = safeCall {
        wipDao.updateStage(stage.toEntity())
    }

    override suspend fun deleteStage(stage: WipStage): Resource<Unit> = safeCall {
        wipDao.deleteStage(stage.toEntity())
    }

    override suspend fun getStageCount(artworkId: String): Int {
        return wipDao.getStageCount(artworkId)
    }
}
