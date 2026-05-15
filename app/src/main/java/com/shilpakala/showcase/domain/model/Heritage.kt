package com.shilpakala.showcase.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Heritage(
    val id: String,
    val title: String,
    val style: String,
    val narrative: String,
    val imageUrl: String?,
    val isAiGenerated: Boolean,
    val language: String,
    val cachedAt: Long
)
