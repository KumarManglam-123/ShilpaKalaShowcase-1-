package com.shilpakala.showcase.domain.usecase.artwork

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.core.utils.IdGenerator
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.ArtworkStatus
import com.shilpakala.showcase.domain.repository.ArtworkRepository
import javax.inject.Inject

class AddArtworkUseCase @Inject constructor(
    private val repository: ArtworkRepository,
    private val idGenerator: IdGenerator
) {
    suspend operator fun invoke(
        shilpiId: String, title: String, material: String, dimensions: String,
        priceRange: String, status: ArtworkStatus, imageUris: List<String>, description: String
    ): Resource<Artwork> {
        if (title.isBlank()) return Resource.Error("Title is required")
        if (material.isBlank()) return Resource.Error("Material is required")
        if (imageUris.isEmpty()) return Resource.Error("At least one image is required")
        if (imageUris.size > 30) return Resource.Error("Maximum 30 images allowed")

        val lastNumber = repository.getLastProductNumber(shilpiId)
        idGenerator.initializeProductCounter(shilpiId, lastNumber)
        val id = idGenerator.generateProductId(shilpiId)
        val now = DateTimeUtils.currentTimeMillis()

        val artwork = Artwork(
            id = id, shilpiId = shilpiId, title = title.trim(), material = material,
            dimensions = dimensions.trim(), priceRange = priceRange.trim(), status = status,
            imageUris = imageUris, description = description.trim(), createdAt = now, updatedAt = now
        )
        return repository.addArtwork(artwork)
    }
}
