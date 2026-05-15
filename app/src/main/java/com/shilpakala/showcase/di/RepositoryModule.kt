package com.shilpakala.showcase.di

import com.shilpakala.showcase.data.repository.*
import com.shilpakala.showcase.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindShilpiRepository(impl: ShilpiRepositoryImpl): ShilpiRepository

    @Binds @Singleton
    abstract fun bindArtworkRepository(impl: ArtworkRepositoryImpl): ArtworkRepository

    @Binds @Singleton
    abstract fun bindHeritageRepository(impl: HeritageRepositoryImpl): HeritageRepository

    @Binds @Singleton
    abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository

    @Binds @Singleton
    abstract fun bindWipRepository(impl: WipRepositoryImpl): WipRepository

    @Binds @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
