package com.shilpakala.showcase.domain.usecase.shilpi

import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.repository.ShilpiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShilpiUseCase @Inject constructor(private val repository: ShilpiRepository) {
    fun observeCurrent(): Flow<Shilpi?> = repository.observeCurrentShilpi()
    fun observeAll(): Flow<List<Shilpi>> = repository.observeAllShilpis()
    suspend fun getRandomShilpi(): Shilpi? = repository.getRandomShilpi()
}
