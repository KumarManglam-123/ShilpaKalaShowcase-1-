package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtworkRepository {
    fun observeAllArtworks(): Flow<List<Artwork>>
    fun observeArtworksByShilpi(shilpiId: String): Flow<List<Artwork>>
    fun observeRecentArtworks(limit: Int): Flow<List<Artwork>>
    fun observeArtworkById(id: String): Flow<Artwork?>
    suspend fun getArtworkById(id: String): Resource<Artwork>
    suspend fun addArtwork(artwork: Artwork): Resource<Artwork>
    suspend fun updateArtwork(artwork: Artwork): Resource<Unit>
    suspend fun deleteArtwork(artwork: Artwork): Resource<Unit>
    suspend fun getArtworkCountByShilpi(shilpiId: String): Int
    suspend fun getLastProductNumber(shilpiId: String): Int
    suspend fun getAllMaterials(): List<String>
    fun searchArtworks(query: String): Flow<List<Artwork>>
    fun getFilteredArtworks(material: String?, status: String?): Flow<List<Artwork>>
}
