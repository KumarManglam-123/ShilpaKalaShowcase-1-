package com.shilpakala.showcase.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class WipStage(
    val id: String,
    val artworkId: String,
    val stageName: WipStageName,
    val photoUri: String?,
    val caption: String,
    val sortOrder: Int,
    val capturedAt: Long,
    val createdAt: Long
)
