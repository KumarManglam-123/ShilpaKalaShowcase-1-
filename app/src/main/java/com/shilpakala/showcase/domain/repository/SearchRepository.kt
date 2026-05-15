package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.SearchFilter
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(filter: SearchFilter): Flow<List<Artwork>>
    suspend fun getAllMaterials(): List<String>
    suspend fun getAllRegions(): List<String>
}
