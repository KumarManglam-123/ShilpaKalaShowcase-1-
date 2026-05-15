package com.shilpakala.showcase.domain.model

data class SearchFilter(
    val query: String = "",
    val material: String? = null,
    val style: String? = null,
    val region: String? = null
) {
    val hasActiveFilters: Boolean
        get() = material != null || style != null || region != null

    fun clearFilters(): SearchFilter = copy(
        material = null,
        style = null,
        region = null
    )
}
