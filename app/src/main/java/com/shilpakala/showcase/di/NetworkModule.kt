package com.shilpakala.showcase.di

import com.shilpakala.showcase.core.constants.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(AppConstants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(AppConstants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.GEMINI_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.GEMINI_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
