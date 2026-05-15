package com.shilpakala.showcase.data.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.resource.safeCall
import com.shilpakala.showcase.data.local.db.dao.ShilpiDao
import com.shilpakala.showcase.data.mapper.toDomain
import com.shilpakala.showcase.data.mapper.toEntity
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.repository.ShilpiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShilpiRepositoryImpl @Inject constructor(
    private val shilpiDao: ShilpiDao
) : ShilpiRepository {

    override fun observeCurrentShilpi(): Flow<Shilpi?> {
        return shilpiDao.observeCurrentShilpi().map { it?.toDomain() }
    }

    override fun observeAllShilpis(): Flow<List<Shilpi>> {
        return shilpiDao.observeAllShilpis().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getShilpiById(id: String): Resource<Shilpi> = safeCall {
        val entity = shilpiDao.getShilpiById(id)
            ?: throw IllegalArgumentException("Shilpi not found: $id")
        entity.toDomain()
    }

    override suspend fun createShilpi(shilpi: Shilpi): Resource<Shilpi> = safeCall {
        shilpiDao.insertShilpi(shilpi.toEntity())
        shilpi
    }

    override suspend fun updateShilpi(shilpi: Shilpi): Resource<Unit> = safeCall {
        shilpiDao.updateShilpi(shilpi.toEntity())
    }

    override suspend fun deleteShilpi(id: String): Resource<Unit> = safeCall {
        val entity = shilpiDao.getShilpiById(id)
            ?: throw IllegalArgumentException("Shilpi not found: $id")
        shilpiDao.deleteShilpi(entity)
    }

    override suspend fun getShilpiCount(): Int = shilpiDao.getShilpiCount()

    override suspend fun getLastShilpiNumber(): Int = shilpiDao.getLastShilpiNumber() ?: 0

    override suspend fun getRandomShilpi(): Shilpi? = shilpiDao.getRandomShilpi()?.toDomain()
}
