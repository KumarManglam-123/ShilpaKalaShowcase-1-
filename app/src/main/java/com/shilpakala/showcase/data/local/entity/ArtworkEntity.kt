package com.shilpakala.showcase.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "artworks",
    foreignKeys = [
        ForeignKey(
            entity = ShilpiEntity::class,
            parentColumns = ["id"],
            childColumns = ["shilpiId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["shilpiId"]),
        Index(value = ["material"]),
        Index(value = ["status"]),
        Index(value = ["createdAt"])
    ]
)
data class ArtworkEntity(
    @PrimaryKey
    val id: String,
    val shilpiId: String,
    val title: String,
    val material: String,
    val dimensions: String,
    val priceRange: String,
    val status: String,
    val imageUris: String, // JSON array stored as string
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)
