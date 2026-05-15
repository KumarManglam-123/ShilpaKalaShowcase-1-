package com.shilpakala.showcase.di

import android.content.Context
import androidx.room.Room
import com.shilpakala.showcase.BuildConfig
import com.shilpakala.showcase.core.constants.AppConstants
import com.shilpakala.showcase.data.local.db.ShilpaKalaDatabase
import com.shilpakala.showcase.data.local.db.dao.ArtworkDao
import com.shilpakala.showcase.data.local.db.dao.HeritageDao
import com.shilpakala.showcase.data.local.db.dao.ShilpiDao
import com.shilpakala.showcase.data.local.db.dao.WipDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShilpaKalaDatabase {
        System.loadLibrary("sqlcipher")
        val passphrase = BuildConfig.DB_PASSPHRASE.toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        return Room.databaseBuilder(
            context,
            ShilpaKalaDatabase::class.java,
            AppConstants.DATABASE_NAME
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides fun provideShilpiDao(db: ShilpaKalaDatabase): ShilpiDao = db.shilpiDao()
    @Provides fun provideArtworkDao(db: ShilpaKalaDatabase): ArtworkDao = db.artworkDao()
    @Provides fun provideHeritageDao(db: ShilpaKalaDatabase): HeritageDao = db.heritageDao()
    @Provides fun provideWipDao(db: ShilpaKalaDatabase): WipDao = db.wipDao()
}
