package com.shilpakala.showcase.core.utils

import com.shilpakala.showcase.core.constants.AppConstants
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Generates unique IDs for Shilpis and Products following the naming convention:
 * - Shilpi: SK-KA-XXXX (e.g., SK-KA-0042)
 * - Product: SKP-KA-XXXX-XXX (e.g., SKP-KA-0042-007)
 *
 * Thread-safe implementation using AtomicInteger.
 */
@Singleton
class IdGenerator @Inject constructor() {

    private val shilpiCounter = AtomicInteger(0)
    private val productCounters = mutableMapOf<String, AtomicInteger>()

    fun initializeCounters(lastShilpiNumber: Int) {
        shilpiCounter.set(lastShilpiNumber)
    }

    fun initializeProductCounter(shilpiId: String, lastProductNumber: Int) {
        productCounters[shilpiId] = AtomicInteger(lastProductNumber)
    }

    fun generateShilpiId(stateCode: String = AppConstants.STATE_CODE_KARNATAKA): String {
        val number = shilpiCounter.incrementAndGet()
        return "${AppConstants.SHILPI_ID_PREFIX}-$stateCode-${number.toString().padStart(4, '0')}"
    }

    fun generateProductId(
        shilpiId: String,
        stateCode: String = AppConstants.STATE_CODE_KARNATAKA
    ): String {
        val counter = productCounters.getOrPut(shilpiId) { AtomicInteger(0) }
        val shilpiNumber = shilpiId.substringAfterLast("-")
        val productNumber = counter.incrementAndGet()
        return "${AppConstants.PRODUCT_ID_PREFIX}-$stateCode-$shilpiNumber-${productNumber.toString().padStart(3, '0')}"
    }
}
