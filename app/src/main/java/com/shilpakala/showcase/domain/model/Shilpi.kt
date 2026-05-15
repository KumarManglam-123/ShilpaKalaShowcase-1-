package com.shilpakala.showcase.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Shilpi(
    val id: String,
    val name: String,
    val village: String,
    val district: String,
    val specialisation: String,
    val profilePhotoUri: String?,
    val createdAt: Long,
    val updatedAt: Long
)
