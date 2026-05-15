package com.shilpakala.showcase.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shilpakala.showcase.data.local.entity.ArtworkEntity
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.ArtworkStatus

private val gson = Gson()

fun ArtworkEntity.toDomain(): Artwork {
    val imageList: List<String> = try {
        val type = object : TypeToken<List<String>>() {}.type
        gson.fromJson(imageUris, type) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }

    return Artwork(
        id = id,
        shilpiId = shilpiId,
        title = title,
        material = material,
        dimensions = dimensions,
        priceRange = priceRange,
        status = ArtworkStatus.fromString(status),
        imageUris = imageList,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Artwork.toEntity(): ArtworkEntity {
    return ArtworkEntity(
        id = id,
        shilpiId = shilpiId,
        title = title,
        material = material,
        dimensions = dimensions,
        priceRange = priceRange,
        status = status.name,
        imageUris = gson.toJson(imageUris),
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
