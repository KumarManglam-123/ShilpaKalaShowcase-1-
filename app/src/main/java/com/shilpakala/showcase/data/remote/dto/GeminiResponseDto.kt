package com.shilpakala.showcase.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeminiResponseDto(
    @SerializedName("candidates")
    val candidates: List<CandidateDto>?
)

data class CandidateDto(
    @SerializedName("content")
    val content: ContentDto?
)

data class ContentDto(
    @SerializedName("parts")
    val parts: List<PartDto>?
)

data class PartDto(
    @SerializedName("text")
    val text: String?
)

data class HeritageDto(
    val id: String,
    val title: String,
    val style: String,
    val narrative: String,
    val imageUrl: String?,
    val language: String
)
