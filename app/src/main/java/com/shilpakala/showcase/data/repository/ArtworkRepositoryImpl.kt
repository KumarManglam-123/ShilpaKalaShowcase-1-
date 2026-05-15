package com.shilpakala.showcase.data.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.resource.safeCall
import com.shilpakala.showcase.data.local.db.dao.ArtworkDao
import com.shilpakala.showcase.data.mapper.toDomain
import com.shilpakala.showcase.data.mapper.toEntity
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.repository.ArtworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val artworkDao: ArtworkDao
) : ArtworkRepository {

    override fun observeAllArtworks(): Flow<List<Artwork>> {
        return artworkDao.observeAllArtworks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeArtworksByShilpi(shilpiId: String): Flow<List<Artwork>> {
        return artworkDao.observeArtworksByShilpi(shilpiId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeRecentArtworks(limit: Int): Flow<List<Artwork>> {
        return artworkDao.observeRecentArtworks(limit).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeArtworkById(id: String): Flow<Artwork?> {
        return artworkDao.observeArtworkById(id).map { it?.toDomain() }
    }

    override suspend fun getArtworkById(id: String): Resource<Artwork> = safeCall {
        val entity = artworkDao.getArtworkById(id)
            ?: throw IllegalArgumentException("Artwork not found: $id")
        entity.toDomain()
    }

    override suspend fun addArtwork(artwork: Artwork): Resource<Artwork> = safeCall {
        artworkDao.insertArtwork(artwork.toEntity())
        artwork
    }

    override suspend fun updateArtwork(artwork: Artwork): Resource<Unit> = safeCall {
        artworkDao.updateArtwork(artwork.toEntity())
    }

    override suspend fun deleteArtwork(artwork: Artwork): Resource<Unit> = safeCall {
        artworkDao.deleteArtwork(artwork.toEntity())
    }

    override suspend fun getArtworkCountByShilpi(shilpiId: String): Int {
        return artworkDao.getArtworkCountByShilpi(shilpiId)
    }

    override suspend fun getLastProductNumber(shilpiId: String): Int {
        return artworkDao.getLastProductNumber(shilpiId) ?: 0
    }

    override suspend fun getAllMaterials(): List<String> {
        return artworkDao.getAllMaterials()
    }

    override fun searchArtworks(query: String): Flow<List<Artwork>> {
        val ftsQuery = query.trim().split(" ").joinToString(" ") { "$it*" }
        return artworkDao.searchArtworks(ftsQuery).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFilteredArtworks(material: String?, status: String?): Flow<List<Artwork>> {
        return artworkDao.getFilteredArtworks(material, status).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
