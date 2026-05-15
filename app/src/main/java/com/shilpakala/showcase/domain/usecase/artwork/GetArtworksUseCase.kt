package com.shilpakala.showcase.domain.usecase.artwork

import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.repository.ArtworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtworksUseCase @Inject constructor(private val repository: ArtworkRepository) {
    fun observeAll(): Flow<List<Artwork>> = repository.observeAllArtworks()
    fun observeByShilpi(shilpiId: String): Flow<List<Artwork>> = repository.observeArtworksByShilpi(shilpiId)
    fun observeRecent(limit: Int = 10): Flow<List<Artwork>> = repository.observeRecentArtworks(limit)
    fun observeById(id: String): Flow<Artwork?> = repository.observeArtworkById(id)
}
