package com.shilpakala.showcase.feature.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.shilpakala.showcase.domain.model.Artwork
import com.shilpakala.showcase.domain.model.ArtworkStatus
import com.shilpakala.showcase.domain.model.Shilpi
import com.shilpakala.showcase.domain.usecase.artwork.GetArtworksUseCase
import com.shilpakala.showcase.domain.usecase.shilpi.GetShilpiUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getShilpiUseCase: GetShilpiUseCase = mockk()
    private val getArtworksUseCase: GetArtworksUseCase = mockk()

    private val testShilpi = Shilpi(
        id = "SK-KA-0001", name = "Test Artisan", village = "Belur",
        district = "Hassan", specialisation = "Stone Sculpture",
        profilePhotoUri = null, createdAt = 0, updatedAt = 0
    )

    private val testArtwork = Artwork(
        id = "SKP-KA-0001-001", shilpiId = "SK-KA-0001", title = "Nandi",
        material = "Granite", dimensions = "24x12", priceRange = "₹15,000",
        status = ArtworkStatus.AVAILABLE, imageUris = emptyList(),
        description = "Test", createdAt = 0, updatedAt = 0
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getShilpiUseCase.observeCurrent() } returns flowOf(testShilpi)
        every { getArtworksUseCase.observeRecent(any()) } returns flowOf(listOf(testArtwork))
        coEvery { getShilpiUseCase.getRandomShilpi() } returns testShilpi
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads shilpi and artworks`() = runTest {
        val viewModel = HomeViewModel(getShilpiUseCase, getArtworksUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            // Allow time for loading
            assertThat(state.currentShilpi).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
