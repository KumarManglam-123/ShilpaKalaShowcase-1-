package com.shilpakala.showcase.domain.usecase.shilpi

import com.google.common.truth.Truth.assertThat
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.IdGenerator
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.repository.ShilpiRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CreateShilpiUseCaseTest {

    private lateinit var useCase: CreateShilpiUseCase
    private val repository: ShilpiRepository = mockk()
    private val idGenerator = IdGenerator()

    @Before
    fun setup() {
        useCase = CreateShilpiUseCase(repository, idGenerator)
    }

    @Test
    fun `create shilpi with valid data returns success`() = runTest {
        coEvery { repository.getLastShilpiNumber() } returns 0
        coEvery { repository.createShilpi(any()) } answers {
            Resource.Success(firstArg<Shilpi>())
        }

        val result = useCase("Ravi Kumar", "Belur", "Hassan", "Stone Sculpture", null)

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        val shilpi = (result as Resource.Success).data
        assertThat(shilpi.name).isEqualTo("Ravi Kumar")
        assertThat(shilpi.id).startsWith("SK-KA-")
    }

    @Test
    fun `create shilpi with blank name returns error`() = runTest {
        val result = useCase("", "Belur", "Hassan", "Stone Sculpture", null)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).message).contains("Name")
    }

    @Test
    fun `create shilpi with blank village returns error`() = runTest {
        val result = useCase("Ravi", "", "Hassan", "Stone Sculpture", null)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `create shilpi with blank specialisation returns error`() = runTest {
        val result = useCase("Ravi", "Belur", "Hassan", "", null)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}
