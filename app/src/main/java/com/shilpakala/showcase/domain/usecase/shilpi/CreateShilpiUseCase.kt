package com.shilpakala.showcase.domain.usecase.shilpi

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.core.utils.IdGenerator
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.repository.ShilpiRepository
import javax.inject.Inject

class CreateShilpiUseCase @Inject constructor(
    private val repository: ShilpiRepository,
    private val idGenerator: IdGenerator
) {
    suspend operator fun invoke(
        name: String, village: String, district: String,
        specialisation: String, profilePhotoUri: String?
    ): Resource<Shilpi> {
        if (name.isBlank()) return Resource.Error("Name is required")
        if (village.isBlank()) return Resource.Error("Village is required")
        if (district.isBlank()) return Resource.Error("District is required")
        if (specialisation.isBlank()) return Resource.Error("Specialisation is required")

        val lastNumber = repository.getLastShilpiNumber()
        idGenerator.initializeCounters(lastNumber)
        val id = idGenerator.generateShilpiId()
        val now = DateTimeUtils.currentTimeMillis()

        val shilpi = Shilpi(
            id = id, name = name.trim(), village = village.trim(),
            district = district.trim(), specialisation = specialisation,
            profilePhotoUri = profilePhotoUri, createdAt = now, updatedAt = now
        )
        return repository.createShilpi(shilpi)
    }
}
