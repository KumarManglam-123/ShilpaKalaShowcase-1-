package com.shilpakala.showcase.data.remote.api

import com.shilpakala.showcase.data.remote.dto.GeminiResponseDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Gemini API service abstraction.
 * In production, this would call the Gemini generative AI endpoint.
 * Currently uses local fallback data via HeritageRepositoryImpl.
 */
interface GeminiApiService {

    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: Map<String, Any>
    ): GeminiResponseDto
}
