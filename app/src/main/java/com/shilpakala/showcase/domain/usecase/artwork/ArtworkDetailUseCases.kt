package com.shilpakala.showcase.domain.usecase.artwork

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.repository.ArtworkRepository
import javax.inject.Inject

class GetArtworkDetailUseCase @Inject constructor(
    private val repository: ArtworkRepository
) {
    suspend operator fun invoke(artworkId: String) = repository.getArtworkById(artworkId)
}

class UpdateArtworkStatusUseCase @Inject constructor(
    private val repository: ArtworkRepository
) {
    suspend operator fun invoke(artworkId: String, status: com.shilpakala.showcase.domain.model.ArtworkStatus): Resource<Unit> {
        val result = repository.getArtworkById(artworkId)
        return when (result) {
            is Resource.Success -> {
                val updated = result.data.copy(
                    status = status,
                    updatedAt = com.shilpakala.showcase.core.utils.DateTimeUtils.currentTimeMillis()
                )
                repository.updateArtwork(updated)
            }
            is Resource.Error -> Resource.Error(result.message)
            is Resource.Loading -> Resource.Loading
        }
    }
}
