package com.shilpakala.showcase.data.mapper

import com.shilpakala.showcase.data.local.entity.ShilpiEntity
import com.shilpakala.showcase.domain.model.Shilpi

fun ShilpiEntity.toDomain(): Shilpi {
    return Shilpi(
        id = id,
        name = name,
        village = village,
        district = district,
        specialisation = specialisation,
        profilePhotoUri = profilePhotoUri,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Shilpi.toEntity(): ShilpiEntity {
    return ShilpiEntity(
        id = id,
        name = name,
        village = village,
        district = district,
        specialisation = specialisation,
        profilePhotoUri = profilePhotoUri,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
