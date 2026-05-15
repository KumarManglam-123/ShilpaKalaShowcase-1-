package com.shilpakala.showcase.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Artwork(
    val id: String,
    val shilpiId: String,
    val title: String,
    val material: String,
    val dimensions: String,
    val priceRange: String,
    val status: ArtworkStatus,
    val imageUris: List<String>,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)
