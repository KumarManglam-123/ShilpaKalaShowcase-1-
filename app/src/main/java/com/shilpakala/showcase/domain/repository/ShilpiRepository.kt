package com.shilpakala.showcase.domain.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.domain.model.Shilpi
import kotlinx.coroutines.flow.Flow

interface ShilpiRepository {
    fun observeCurrentShilpi(): Flow<Shilpi?>
    fun observeAllShilpis(): Flow<List<Shilpi>>
    suspend fun getShilpiById(id: String): Resource<Shilpi>
    suspend fun createShilpi(shilpi: Shilpi): Resource<Shilpi>
    suspend fun updateShilpi(shilpi: Shilpi): Resource<Unit>
    suspend fun deleteShilpi(id: String): Resource<Unit>
    suspend fun getShilpiCount(): Int
    suspend fun getLastShilpiNumber(): Int
    suspend fun getRandomShilpi(): Shilpi?
}
