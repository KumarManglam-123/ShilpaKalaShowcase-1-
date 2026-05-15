package com.shilpakala.showcase.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "heritage",
    indices = [
        Index(value = ["style"]),
        Index(value = ["language"])
    ]
)
data class HeritageEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val style: String,
    val narrative: String,
    val imageUrl: String?,
    val isAiGenerated: Boolean,
    val language: String,
    val cachedAt: Long
)
