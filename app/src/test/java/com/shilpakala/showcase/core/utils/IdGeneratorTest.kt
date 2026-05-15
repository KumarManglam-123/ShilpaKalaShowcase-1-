package com.shilpakala.showcase.core.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class IdGeneratorTest {

    private lateinit var idGenerator: IdGenerator

    @Before
    fun setup() {
        idGenerator = IdGenerator()
    }

    @Test
    fun `generateShilpiId creates valid format`() {
        idGenerator.initializeCounters(0)
        val id = idGenerator.generateShilpiId()
        assertThat(id).matches("SK-KA-\\d{4}")
        assertThat(id).isEqualTo("SK-KA-0001")
    }

    @Test
    fun `generateShilpiId increments correctly`() {
        idGenerator.initializeCounters(41)
        val id = idGenerator.generateShilpiId()
        assertThat(id).isEqualTo("SK-KA-0042")
    }

    @Test
    fun `generateProductId creates valid format`() {
        val shilpiId = "SK-KA-0042"
        idGenerator.initializeProductCounter(shilpiId, 6)
        val id = idGenerator.generateProductId(shilpiId)
        assertThat(id).isEqualTo("SKP-KA-0042-007")
    }

    @Test
    fun `multiple product ids increment correctly`() {
        val shilpiId = "SK-KA-0001"
        idGenerator.initializeProductCounter(shilpiId, 0)
        val id1 = idGenerator.generateProductId(shilpiId)
        val id2 = idGenerator.generateProductId(shilpiId)
        assertThat(id1).endsWith("-001")
        assertThat(id2).endsWith("-002")
    }
}
