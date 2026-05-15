package com.shilpakala.showcase.data.local.entity

import androidx.room.Entity
import androidx.room.Fts4

/**
 * Full-text search virtual table for artwork search under 1 second.
 * Mirrors searchable fields from ArtworkEntity.
 */
@Fts4(contentEntity = ArtworkEntity::class)
@Entity(tableName = "artwork_fts")
data class ArtworkFts(
    val title: String,
    val material: String,
    val description: String
)
