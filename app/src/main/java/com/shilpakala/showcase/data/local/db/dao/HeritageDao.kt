package com.shilpakala.showcase.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shilpakala.showcase.data.local.entity.HeritageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeritageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeritage(heritage: HeritageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeritageList(heritageList: List<HeritageEntity>)

    @Update
    suspend fun updateHeritage(heritage: HeritageEntity)

    @Delete
    suspend fun deleteHeritage(heritage: HeritageEntity)

    @Query("SELECT * FROM heritage WHERE id = :id")
    suspend fun getHeritageById(id: String): HeritageEntity?

    @Query("SELECT * FROM heritage WHERE id = :id")
    fun observeHeritageById(id: String): Flow<HeritageEntity?>

    @Query("SELECT * FROM heritage WHERE language = :language ORDER BY title ASC")
    fun observeHeritageByLanguage(language: String): Flow<List<HeritageEntity>>

    @Query("SELECT * FROM heritage WHERE style = :style AND language = :language LIMIT 1")
    suspend fun getHeritageByStyle(style: String, language: String): HeritageEntity?

    @Query("SELECT * FROM heritage ORDER BY cachedAt DESC")
    fun observeAllHeritage(): Flow<List<HeritageEntity>>

    @Query("DELETE FROM heritage WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)

    @Query("DELETE FROM heritage")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM heritage WHERE language = :language")
    suspend fun getCount(language: String): Int
}
