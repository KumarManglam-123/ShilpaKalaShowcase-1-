package com.shilpakala.showcase.`data`.local.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shilpakala.showcase.`data`.local.entity.WipStageEntity
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
public class WipDao_Impl(
  __db: RoomDatabase,
) : WipDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWipStageEntity: EntityInsertAdapter<WipStageEntity>

  private val __deleteAdapterOfWipStageEntity: EntityDeleteOrUpdateAdapter<WipStageEntity>

  private val __updateAdapterOfWipStageEntity: EntityDeleteOrUpdateAdapter<WipStageEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWipStageEntity = object : EntityInsertAdapter<WipStageEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `wip_stages` (`id`,`artworkId`,`stageName`,`photoUri`,`caption`,`sortOrder`,`capturedAt`,`createdAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WipStageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.artworkId)
        statement.bindText(3, entity.stageName)
        val _tmpPhotoUri: String? = entity.photoUri
        if (_tmpPhotoUri == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPhotoUri)
        }
        statement.bindText(5, entity.caption)
        statement.bindLong(6, entity.sortOrder.toLong())
        statement.bindLong(7, entity.capturedAt)
        statement.bindLong(8, entity.createdAt)
      }
    }
    this.__deleteAdapterOfWipStageEntity = object : EntityDeleteOrUpdateAdapter<WipStageEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `wip_stages` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: WipStageEntity) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfWipStageEntity = object : EntityDeleteOrUpdateAdapter<WipStageEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `wip_stages` SET `id` = ?,`artworkId` = ?,`stageName` = ?,`photoUri` = ?,`caption` = ?,`sortOrder` = ?,`capturedAt` = ?,`createdAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: WipStageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.artworkId)
        statement.bindText(3, entity.stageName)
        val _tmpPhotoUri: String? = entity.photoUri
        if (_tmpPhotoUri == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpPhotoUri)
        }
        statement.bindText(5, entity.caption)
        statement.bindLong(6, entity.sortOrder.toLong())
        statement.bindLong(7, entity.capturedAt)
        statement.bindLong(8, entity.createdAt)
        statement.bindText(9, entity.id)
      }
    }
  }

  public override suspend fun insertStage(stage: WipStageEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfWipStageEntity.insertAndReturnId(_connection, stage)
    _result
  }

  public override suspend fun deleteStage(stage: WipStageEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfWipStageEntity.handle(_connection, stage)
  }

  public override suspend fun updateStage(stage: WipStageEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfWipStageEntity.handle(_connection, stage)
  }

  public override suspend fun getStageById(id: String): WipStageEntity? {
    val _sql: String = "SELECT * FROM wip_stages WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfArtworkId: Int = getColumnIndexOrThrow(_stmt, "artworkId")
        val _columnIndexOfStageName: Int = getColumnIndexOrThrow(_stmt, "stageName")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCaption: Int = getColumnIndexOrThrow(_stmt, "caption")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _columnIndexOfCapturedAt: Int = getColumnIndexOrThrow(_stmt, "capturedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: WipStageEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpArtworkId: String
          _tmpArtworkId = _stmt.getText(_columnIndexOfArtworkId)
          val _tmpStageName: String
          _tmpStageName = _stmt.getText(_columnIndexOfStageName)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCaption: String
          _tmpCaption = _stmt.getText(_columnIndexOfCaption)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCapturedAt: Long
          _tmpCapturedAt = _stmt.getLong(_columnIndexOfCapturedAt)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = WipStageEntity(_tmpId,_tmpArtworkId,_tmpStageName,_tmpPhotoUri,_tmpCaption,_tmpSortOrder,_tmpCapturedAt,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeStagesByArtwork(artworkId: String): Flow<List<WipStageEntity>> {
    val _sql: String = "SELECT * FROM wip_stages WHERE artworkId = ? ORDER BY sortOrder ASC"
    return createFlow(__db, false, arrayOf("wip_stages")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfArtworkId: Int = getColumnIndexOrThrow(_stmt, "artworkId")
        val _columnIndexOfStageName: Int = getColumnIndexOrThrow(_stmt, "stageName")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCaption: Int = getColumnIndexOrThrow(_stmt, "caption")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _columnIndexOfCapturedAt: Int = getColumnIndexOrThrow(_stmt, "capturedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<WipStageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WipStageEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpArtworkId: String
          _tmpArtworkId = _stmt.getText(_columnIndexOfArtworkId)
          val _tmpStageName: String
          _tmpStageName = _stmt.getText(_columnIndexOfStageName)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCaption: String
          _tmpCaption = _stmt.getText(_columnIndexOfCaption)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCapturedAt: Long
          _tmpCapturedAt = _stmt.getLong(_columnIndexOfCapturedAt)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = WipStageEntity(_tmpId,_tmpArtworkId,_tmpStageName,_tmpPhotoUri,_tmpCaption,_tmpSortOrder,_tmpCapturedAt,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getStagesByArtwork(artworkId: String): List<WipStageEntity> {
    val _sql: String = "SELECT * FROM wip_stages WHERE artworkId = ? ORDER BY sortOrder ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfArtworkId: Int = getColumnIndexOrThrow(_stmt, "artworkId")
        val _columnIndexOfStageName: Int = getColumnIndexOrThrow(_stmt, "stageName")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCaption: Int = getColumnIndexOrThrow(_stmt, "caption")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _columnIndexOfCapturedAt: Int = getColumnIndexOrThrow(_stmt, "capturedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: MutableList<WipStageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WipStageEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpArtworkId: String
          _tmpArtworkId = _stmt.getText(_columnIndexOfArtworkId)
          val _tmpStageName: String
          _tmpStageName = _stmt.getText(_columnIndexOfStageName)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCaption: String
          _tmpCaption = _stmt.getText(_columnIndexOfCaption)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCapturedAt: Long
          _tmpCapturedAt = _stmt.getLong(_columnIndexOfCapturedAt)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = WipStageEntity(_tmpId,_tmpArtworkId,_tmpStageName,_tmpPhotoUri,_tmpCaption,_tmpSortOrder,_tmpCapturedAt,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getStageCount(artworkId: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM wip_stages WHERE artworkId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
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

  public override suspend fun getMaxSortOrder(artworkId: String): Int? {
    val _sql: String = "SELECT MAX(sortOrder) FROM wip_stages WHERE artworkId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
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

  public override suspend fun getStageByName(artworkId: String, stageName: String): WipStageEntity? {
    val _sql: String = """
        |
        |        SELECT * FROM wip_stages 
        |        WHERE artworkId = ? AND stageName = ? 
        |        LIMIT 1
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
        _argIndex = 2
        _stmt.bindText(_argIndex, stageName)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfArtworkId: Int = getColumnIndexOrThrow(_stmt, "artworkId")
        val _columnIndexOfStageName: Int = getColumnIndexOrThrow(_stmt, "stageName")
        val _columnIndexOfPhotoUri: Int = getColumnIndexOrThrow(_stmt, "photoUri")
        val _columnIndexOfCaption: Int = getColumnIndexOrThrow(_stmt, "caption")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sortOrder")
        val _columnIndexOfCapturedAt: Int = getColumnIndexOrThrow(_stmt, "capturedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _result: WipStageEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpArtworkId: String
          _tmpArtworkId = _stmt.getText(_columnIndexOfArtworkId)
          val _tmpStageName: String
          _tmpStageName = _stmt.getText(_columnIndexOfStageName)
          val _tmpPhotoUri: String?
          if (_stmt.isNull(_columnIndexOfPhotoUri)) {
            _tmpPhotoUri = null
          } else {
            _tmpPhotoUri = _stmt.getText(_columnIndexOfPhotoUri)
          }
          val _tmpCaption: String
          _tmpCaption = _stmt.getText(_columnIndexOfCaption)
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCapturedAt: Long
          _tmpCapturedAt = _stmt.getLong(_columnIndexOfCapturedAt)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result = WipStageEntity(_tmpId,_tmpArtworkId,_tmpStageName,_tmpPhotoUri,_tmpCaption,_tmpSortOrder,_tmpCapturedAt,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteStagesByArtwork(artworkId: String) {
    val _sql: String = "DELETE FROM wip_stages WHERE artworkId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, artworkId)
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
