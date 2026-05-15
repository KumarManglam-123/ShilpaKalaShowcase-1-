package com.shilpakala.showcase.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shilpakala.showcase.data.local.entity.ArtworkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: ArtworkEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtworks(artworks: List<ArtworkEntity>)

    @Update
    suspend fun updateArtwork(artwork: ArtworkEntity)

    @Delete
    suspend fun deleteArtwork(artwork: ArtworkEntity)

    @Query("SELECT * FROM artworks WHERE id = :id")
    suspend fun getArtworkById(id: String): ArtworkEntity?

    @Query("SELECT * FROM artworks WHERE id = :id")
    fun observeArtworkById(id: String): Flow<ArtworkEntity?>

    @Query("SELECT * FROM artworks WHERE shilpiId = :shilpiId ORDER BY createdAt DESC")
    fun observeArtworksByShilpi(shilpiId: String): Flow<List<ArtworkEntity>>

    @Query("SELECT * FROM artworks ORDER BY createdAt DESC")
    fun observeAllArtworks(): Flow<List<ArtworkEntity>>

    @Query("SELECT * FROM artworks ORDER BY createdAt DESC LIMIT :limit")
    fun observeRecentArtworks(limit: Int): Flow<List<ArtworkEntity>>

    @Query("SELECT * FROM artworks WHERE material = :material ORDER BY createdAt DESC")
    fun getArtworksByMaterial(material: String): Flow<List<ArtworkEntity>>

    @Query("SELECT * FROM artworks WHERE status = :status ORDER BY createdAt DESC")
    fun getArtworksByStatus(status: String): Flow<List<ArtworkEntity>>

    @Query("SELECT COUNT(*) FROM artworks WHERE shilpiId = :shilpiId")
    suspend fun getArtworkCountByShilpi(shilpiId: String): Int

    @Query("""
        SELECT MAX(CAST(SUBSTR(id, LENGTH(id) - 2) AS INTEGER)) 
        FROM artworks 
        WHERE shilpiId = :shilpiId
    """)
    suspend fun getLastProductNumber(shilpiId: String): Int?

    // FTS search query
    @Query("""
        SELECT artworks.* FROM artworks
        JOIN artwork_fts ON artworks.rowid = artwork_fts.rowid
        WHERE artwork_fts MATCH :query
        ORDER BY artworks.createdAt DESC
    """)
    fun searchArtworks(query: String): Flow<List<ArtworkEntity>>

    // Filtered search
    @Query("""
        SELECT * FROM artworks 
        WHERE (:material IS NULL OR material = :material)
        AND (:status IS NULL OR status = :status)
        ORDER BY createdAt DESC
    """)
    fun getFilteredArtworks(
        material: String?,
        status: String?
    ): Flow<List<ArtworkEntity>>

    @Query("SELECT DISTINCT material FROM artworks")
    suspend fun getAllMaterials(): List<String>

    @Query("DELETE FROM artworks WHERE shilpiId = :shilpiId")
    suspend fun deleteArtworksByShilpi(shilpiId: String)
}
