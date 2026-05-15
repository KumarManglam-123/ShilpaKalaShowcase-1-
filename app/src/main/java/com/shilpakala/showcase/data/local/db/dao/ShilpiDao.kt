package com.shilpakala.showcase.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shilpakala.showcase.data.local.entity.ShilpiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShilpiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShilpi(shilpi: ShilpiEntity): Long

    @Update
    suspend fun updateShilpi(shilpi: ShilpiEntity)

    @Delete
    suspend fun deleteShilpi(shilpi: ShilpiEntity)

    @Query("SELECT * FROM shilpis WHERE id = :id")
    suspend fun getShilpiById(id: String): ShilpiEntity?

    @Query("SELECT * FROM shilpis WHERE id = :id")
    fun observeShilpiById(id: String): Flow<ShilpiEntity?>

    @Query("SELECT * FROM shilpis ORDER BY createdAt DESC")
    fun observeAllShilpis(): Flow<List<ShilpiEntity>>

    @Query("SELECT * FROM shilpis ORDER BY createdAt DESC LIMIT 1")
    fun observeCurrentShilpi(): Flow<ShilpiEntity?>

    @Query("SELECT * FROM shilpis ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCurrentShilpi(): ShilpiEntity?

    @Query("SELECT COUNT(*) FROM shilpis")
    suspend fun getShilpiCount(): Int

    @Query("SELECT MAX(CAST(SUBSTR(id, LENGTH(id) - 3) AS INTEGER)) FROM shilpis")
    suspend fun getLastShilpiNumber(): Int?

    @Query("SELECT * FROM shilpis WHERE name LIKE '%' || :query || '%' OR specialisation LIKE '%' || :query || '%'")
    fun searchShilpis(query: String): Flow<List<ShilpiEntity>>

    @Query("SELECT * FROM shilpis ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomShilpi(): ShilpiEntity?
}
