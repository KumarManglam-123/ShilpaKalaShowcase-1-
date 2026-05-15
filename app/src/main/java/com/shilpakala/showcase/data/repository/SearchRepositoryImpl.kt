package com.shilpakala.showcase.data.repository

import com.shilpakala.showcase.data.local.db.dao.ArtworkDao
import com.shilpakala.showcase.data.local.db.dao.ShilpiDao
import com.shilpakala.showcase.data.mapper.toDomain
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.SearchFilter
import com.shilpakala.showcase.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val artworkDao: ArtworkDao,
    private val shilpiDao: ShilpiDao
) : SearchRepository {
    override fun search(filter: SearchFilter): Flow<List<Artwork>> {
        if (filter.query.isBlank() && !filter.hasActiveFilters) return flowOf(emptyList())
        return if (filter.query.isNotBlank()) {
            val ftsQuery = filter.query.trim().split(" ").joinToString(" ") { "$it*" }
            artworkDao.searchArtworks(ftsQuery).map { entities ->
                var results = entities.map { it.toDomain() }
                if (filter.material != null) results = results.filter { it.material.equals(filter.material, true) }
                results
            }
        } else {
            artworkDao.getFilteredArtworks(filter.material, null).map { e -> e.map { it.toDomain() } }
        }
    }
    override suspend fun getAllMaterials(): List<String> = artworkDao.getAllMaterials()
    override suspend fun getAllRegions(): List<String> = listOf("Hassan","Mysuru","Bengaluru","Mandya","Tumkur","Shimoga","Dharwad")
}
