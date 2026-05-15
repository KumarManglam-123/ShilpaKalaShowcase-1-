package com.shilpakala.showcase.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shilpakala.showcase.data.local.db.dao.ArtworkDao
import com.shilpakala.showcase.data.local.db.dao.HeritageDao
import com.shilpakala.showcase.data.local.db.dao.ShilpiDao
import com.shilpakala.showcase.data.local.db.dao.WipDao
import com.shilpakala.showcase.data.local.entity.ArtworkEntity
import com.shilpakala.showcase.data.local.entity.ArtworkFts
import com.shilpakala.showcase.data.local.entity.HeritageEntity
import com.shilpakala.showcase.data.local.entity.ShilpiEntity
import com.shilpakala.showcase.data.local.entity.WipStageEntity

@Database(
    entities = [
        ShilpiEntity::class,
        ArtworkEntity::class,
        ArtworkFts::class,
        HeritageEntity::class,
        WipStageEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ShilpaKalaDatabase : RoomDatabase() {

    abstract fun shilpiDao(): ShilpiDao
    abstract fun artworkDao(): ArtworkDao
    abstract fun heritageDao(): HeritageDao
    abstract fun wipDao(): WipDao
}
