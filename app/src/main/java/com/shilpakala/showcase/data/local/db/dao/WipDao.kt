package com.shilpakala.showcase.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shilpakala.showcase.data.local.entity.WipStageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStage(stage: WipStageEntity): Long

    @Update
    suspend fun updateStage(stage: WipStageEntity)

    @Delete
    suspend fun deleteStage(stage: WipStageEntity)

    @Query("SELECT * FROM wip_stages WHERE id = :id")
    suspend fun getStageById(id: String): WipStageEntity?

    @Query("SELECT * FROM wip_stages WHERE artworkId = :artworkId ORDER BY sortOrder ASC")
    fun observeStagesByArtwork(artworkId: String): Flow<List<WipStageEntity>>

    @Query("SELECT * FROM wip_stages WHERE artworkId = :artworkId ORDER BY sortOrder ASC")
    suspend fun getStagesByArtwork(artworkId: String): List<WipStageEntity>

    @Query("SELECT COUNT(*) FROM wip_stages WHERE artworkId = :artworkId")
    suspend fun getStageCount(artworkId: String): Int

    @Query("SELECT MAX(sortOrder) FROM wip_stages WHERE artworkId = :artworkId")
    suspend fun getMaxSortOrder(artworkId: String): Int?

    @Query("DELETE FROM wip_stages WHERE artworkId = :artworkId")
    suspend fun deleteStagesByArtwork(artworkId: String)

    @Query("""
        SELECT * FROM wip_stages 
        WHERE artworkId = :artworkId AND stageName = :stageName 
        LIMIT 1
    """)
    suspend fun getStageByName(artworkId: String, stageName: String): WipStageEntity?
}
