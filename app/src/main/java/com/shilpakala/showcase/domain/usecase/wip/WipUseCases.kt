package com.shilpakala.showcase.domain.usecase.wip

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.domain.model.WipStage
import com.shilpakala.showcase.domain.model.WipStageName
import com.shilpakala.showcase.domain.repository.WipRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AddWipStageUseCase @Inject constructor(private val repository: WipRepository) {
    suspend operator fun invoke(
        artworkId: String, stageName: WipStageName, photoUri: String?, caption: String
    ): Resource<WipStage> {
        val count = repository.getStageCount(artworkId)
        val now = DateTimeUtils.currentTimeMillis()
        val stage = WipStage(
            id = UUID.randomUUID().toString(), artworkId = artworkId, stageName = stageName,
            photoUri = photoUri, caption = caption.trim(), sortOrder = stageName.order,
            capturedAt = now, createdAt = now
        )
        return repository.addStage(stage)
    }
}

class GetWipTimelineUseCase @Inject constructor(private val repository: WipRepository) {
    operator fun invoke(artworkId: String): Flow<List<WipStage>> = repository.observeStagesByArtwork(artworkId)
}
