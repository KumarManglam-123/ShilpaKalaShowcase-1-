package com.shilpakala.showcase.domain.model

enum class WipStageName(val displayName: String, val order: Int) {
    RAW_STONE("Raw Stone", 0),
    ROUGH_SHAPING("Rough Shaping", 1),
    DETAIL_CARVING("Detail Carving", 2),
    FINISHING("Finishing", 3),
    COMPLETED("Completed", 4);

    companion object {
        fun fromString(value: String): WipStageName {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: RAW_STONE
        }

        fun getAll(): List<WipStageName> = entries.sortedBy { it.order }
    }
}
