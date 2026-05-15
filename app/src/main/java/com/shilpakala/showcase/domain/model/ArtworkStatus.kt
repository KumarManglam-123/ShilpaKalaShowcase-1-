package com.shilpakala.showcase.domain.model

enum class ArtworkStatus(val displayName: String) {
    AVAILABLE("Available"),
    SOLD("Sold"),
    COMMISSION_ONLY("Commission Only");

    companion object {
        fun fromString(value: String): ArtworkStatus {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: AVAILABLE
        }
    }
}
