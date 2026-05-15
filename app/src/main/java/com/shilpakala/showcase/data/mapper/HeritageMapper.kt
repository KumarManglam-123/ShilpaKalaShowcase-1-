package com.shilpakala.showcase.data.mapper

import com.shilpakala.showcase.data.local.entity.HeritageEntity
import com.shilpakala.showcase.data.local.entity.WipStageEntity
import com.shilpakala.showcase.domain.model.Heritage
import com.shilpakala.showcase.domain.model.WipStage
import com.shilpakala.showcase.domain.model.WipStageName

fun HeritageEntity.toDomain(): Heritage {
    return Heritage(
        id = id,
        title = title,
        style = style,
        narrative = narrative,
        imageUrl = imageUrl,
        isAiGenerated = isAiGenerated,
        language = language,
        cachedAt = cachedAt
    )
}

fun Heritage.toEntity(): HeritageEntity {
    return HeritageEntity(
        id = id,
        title = title,
        style = style,
        narrative = narrative,
        imageUrl = imageUrl,
        isAiGenerated = isAiGenerated,
        language = language,
        cachedAt = cachedAt
    )
}

fun WipStageEntity.toDomain(): WipStage {
    return WipStage(
        id = id,
        artworkId = artworkId,
        stageName = WipStageName.fromString(stageName),
        photoUri = photoUri,
        caption = caption,
        sortOrder = sortOrder,
        capturedAt = capturedAt,
        createdAt = createdAt
    )
}

fun WipStage.toEntity(): WipStageEntity {
    return WipStageEntity(
        id = id,
        artworkId = artworkId,
        stageName = stageName.name,
        photoUri = photoUri,
        caption = caption,
        sortOrder = sortOrder,
        capturedAt = capturedAt,
        createdAt = createdAt
    )
}
