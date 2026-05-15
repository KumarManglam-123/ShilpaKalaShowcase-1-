package com.shilpakala.showcase.domain.usecase.artwork

import com.google.common.truth.Truth.assertThat
import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.utils.IdGenerator
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.ArtworkStatus
import com.shilpakala.showcase.domain.repository.ArtworkRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddArtworkUseCaseTest {

    private lateinit var useCase: AddArtworkUseCase
    private val repository: ArtworkRepository = mockk()
    private val idGenerator = IdGenerator()

    @Before
    fun setup() {
        useCase = AddArtworkUseCase(repository, idGenerator)
    }

    @Test
    fun `add artwork with valid data returns success`() = runTest {
        coEvery { repository.getLastProductNumber(any()) } returns 0
        coEvery { repository.addArtwork(any()) } answers { Resource.Success(firstArg<Artwork>()) }

        val result = useCase(
            "SK-KA-0001", "Nandi Statue", "Granite", "24x12x8",
            "₹15,000", ArtworkStatus.AVAILABLE, listOf("uri1"), "A beautiful Nandi"
        )

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        val artwork = (result as Resource.Success).data
        assertThat(artwork.title).isEqualTo("Nandi Statue")
        assertThat(artwork.id).startsWith("SKP-KA-")
    }

    @Test
    fun `add artwork with blank title returns error`() = runTest {
        val result = useCase("SK-KA-0001", "", "Granite", "", "", ArtworkStatus.AVAILABLE, listOf("uri"), "")
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `add artwork with empty images returns error`() = runTest {
        val result = useCase("SK-KA-0001", "Title", "Granite", "", "", ArtworkStatus.AVAILABLE, emptyList(), "")
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `add artwork with too many images returns error`() = runTest {
        val uris = (1..31).map { "uri$it" }
        val result = useCase("SK-KA-0001", "Title", "Granite", "", "", ArtworkStatus.AVAILABLE, uris, "")
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}
