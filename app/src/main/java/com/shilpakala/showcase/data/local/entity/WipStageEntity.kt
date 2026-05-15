package com.shilpakala.showcase.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "wip_stages",
    foreignKeys = [
        ForeignKey(
            entity = ArtworkEntity::class,
            parentColumns = ["id"],
            childColumns = ["artworkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["artworkId"]),
        Index(value = ["sortOrder"])
    ]
)
data class WipStageEntity(
    @PrimaryKey
    val id: String,
    val artworkId: String,
    val stageName: String,
    val photoUri: String?,
    val caption: String,
    val sortOrder: Int,
    val capturedAt: Long,
    val createdAt: Long
)
