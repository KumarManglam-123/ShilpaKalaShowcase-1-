package com.shilpakala.showcase.`data`.local.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shilpakala.showcase.`data`.local.entity.ArtworkEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ArtworkDao_Impl(
  __db: RoomDatabase,
) : ArtworkDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfArtworkEntity: EntityInsertAdapter<ArtworkEntity>

  private val __deleteAdapterOfArtworkEntity: EntityDeleteOrUpdateAdapter<ArtworkEntity>

  private val __updateAdapterOfArtworkEntity: EntityDeleteOrUpdateAdapter<ArtworkEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfArtworkEntity = object : EntityInsertAdapter<ArtworkEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `artworks` (`id`,`shilpiId`,`title`,`material`,`dimensions`,`priceRange`,`status`,`imageUris`,`description`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ArtworkEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.shilpiId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.material)
        statement.bindText(5, entity.dimensions)
        statement.bindText(6, entity.priceRange)
        statement.bindText(7, entity.status)
        statement.bindText(8, entity.imageUris)
        statement.bindText(9, entity.description)
        statement.bindLong(10, entity.createdAt)
        statement.bindLong(11, entity.updatedAt)
      }
    }
    this.__deleteAdapterOfArtworkEntity = object : EntityDeleteOrUpdateAdapter<ArtworkEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `artworks` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ArtworkEntity) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfArtworkEntity = object : EntityDeleteOrUpdateAdapter<ArtworkEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `artworks` SET `id` = ?,`shilpiId` = ?,`title` = ?,`material` = ?,`dimensions` = ?,`priceRange` = ?,`status` = ?,`imageUris` = ?,`description` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ArtworkEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.shilpiId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.material)
        statement.bindText(5, entity.dimensions)
        statement.bindText(6, entity.priceRange)
        statement.bindText(7, entity.status)
        statement.bindText(8, entity.imageUris)
        statement.bindText(9, entity.description)
        statement.bindLong(10, entity.createdAt)
        statement.bindLong(11, entity.updatedAt)
        statement.bindText(12, entity.id)
      }
    }
  }

  public override suspend fun insertArtwork(artwork: ArtworkEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfArtworkEntity.insertAndReturnId(_connection, artwork)
    _result
  }

  public override suspend fun insertArtworks(artworks: List<ArtworkEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfArtworkEntity.insert(_connection, artworks)
  }

  public override suspend fun deleteArtwork(artwork: ArtworkEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfArtworkEntity.handle(_connection, artwork)
  }

  public override suspend fun updateArtwork(artwork: ArtworkEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfArtworkEntity.handle(_connection, artwork)
  }

  public override suspend fun getArtworkById(id: String): ArtworkEntity? {
    val _sql: String = "SELECT * FROM artworks WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ArtworkEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeArtworkById(id: String): Flow<ArtworkEntity?> {
    val _sql: String = "SELECT * FROM artworks WHERE id = ?"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ArtworkEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeArtworksByShilpi(shilpiId: String): Flow<List<ArtworkEntity>> {
    val _sql: String = "SELECT * FROM artworks WHERE shilpiId = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, shilpiId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeAllArtworks(): Flow<List<ArtworkEntity>> {
    val _sql: String = "SELECT * FROM artworks ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeRecentArtworks(limit: Int): Flow<List<ArtworkEntity>> {
    val _sql: String = "SELECT * FROM artworks ORDER BY createdAt DESC LIMIT ?"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getArtworksByMaterial(material: String): Flow<List<ArtworkEntity>> {
    val _sql: String = "SELECT * FROM artworks WHERE material = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, material)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getArtworksByStatus(status: String): Flow<List<ArtworkEntity>> {
    val _sql: String = "SELECT * FROM artworks WHERE status = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getArtworkCountByShilpi(shilpiId: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM artworks WHERE shilpiId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, shilpiId)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLastProductNumber(shilpiId: String): Int? {
    val _sql: String = """
        |
        |        SELECT MAX(CAST(SUBSTR(id, LENGTH(id) - 2) AS INTEGER)) 
        |        FROM artworks 
        |        WHERE shilpiId = ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, shilpiId)
        val _result: Int?
        if (_stmt.step()) {
          val _tmp: Int?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0).toInt()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchArtworks(query: String): Flow<List<ArtworkEntity>> {
    val _sql: String = """
        |
        |        SELECT artworks.* FROM artworks
        |        JOIN artwork_fts ON artworks.rowid = artwork_fts.rowid
        |        WHERE artwork_fts MATCH ?
        |        ORDER BY artworks.createdAt DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("artworks", "artwork_fts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getFilteredArtworks(material: String?, status: String?): Flow<List<ArtworkEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM artworks 
        |        WHERE (? IS NULL OR material = ?)
        |        AND (? IS NULL OR status = ?)
        |        ORDER BY createdAt DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("artworks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (material == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, material)
        }
        _argIndex = 2
        if (material == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, material)
        }
        _argIndex = 3
        if (status == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, status)
        }
        _argIndex = 4
        if (status == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, status)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfShilpiId: Int = getColumnIndexOrThrow(_stmt, "shilpiId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfMaterial: Int = getColumnIndexOrThrow(_stmt, "material")
        val _columnIndexOfDimensions: Int = getColumnIndexOrThrow(_stmt, "dimensions")
        val _columnIndexOfPriceRange: Int = getColumnIndexOrThrow(_stmt, "priceRange")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfImageUris: Int = getColumnIndexOrThrow(_stmt, "imageUris")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ArtworkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ArtworkEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpShilpiId: String
          _tmpShilpiId = _stmt.getText(_columnIndexOfShilpiId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpMaterial: String
          _tmpMaterial = _stmt.getText(_columnIndexOfMaterial)
          val _tmpDimensions: String
          _tmpDimensions = _stmt.getText(_columnIndexOfDimensions)
          val _tmpPriceRange: String
          _tmpPriceRange = _stmt.getText(_columnIndexOfPriceRange)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpImageUris: String
          _tmpImageUris = _stmt.getText(_columnIndexOfImageUris)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ArtworkEntity(_tmpId,_tmpShilpiId,_tmpTitle,_tmpMaterial,_tmpDimensions,_tmpPriceRange,_tmpStatus,_tmpImageUris,_tmpDescription,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllMaterials(): List<String> {
    val _sql: String = "SELECT DISTINCT material FROM artworks"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteArtworksByShilpi(shilpiId: String) {
    val _sql: String = "DELETE FROM artworks WHERE shilpiId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, shilpiId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
