package com.shilpakala.showcase.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shilpis",
    indices = [
        Index(value = ["name"]),
        Index(value = ["specialisation"])
    ]
)
data class ShilpiEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val village: String,
    val district: String,
    val specialisation: String,
    val profilePhotoUri: String?,
    val createdAt: Long,
    val updatedAt: Long
)
