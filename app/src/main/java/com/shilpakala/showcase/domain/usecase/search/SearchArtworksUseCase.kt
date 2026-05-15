package com.shilpakala.showcase.domain.usecase.search

import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.SearchFilter
import com.shilpakala.showcase.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchArtworksUseCase @Inject constructor(private val repository: SearchRepository) {
    operator fun invoke(filter: SearchFilter): Flow<List<Artwork>> = repository.search(filter)
    suspend fun getMaterials(): List<String> = repository.getAllMaterials()
    suspend fun getRegions(): List<String> = repository.getAllRegions()
}
