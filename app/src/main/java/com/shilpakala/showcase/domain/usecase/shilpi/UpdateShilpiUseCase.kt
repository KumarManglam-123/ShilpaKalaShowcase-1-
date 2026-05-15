package com.shilpakala.showcase.domain.usecase.shilpi

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.repository.ShilpiRepository
import javax.inject.Inject

class UpdateShilpiUseCase @Inject constructor(
    private val repository: ShilpiRepository
) {
    suspend operator fun invoke(shilpi: Shilpi): Resource<Unit> {
        if (shilpi.name.isBlank()) return Resource.Error("Name is required")
        val updated = shilpi.copy(updatedAt = DateTimeUtils.currentTimeMillis())
        return repository.updateShilpi(updated)
    }
}
