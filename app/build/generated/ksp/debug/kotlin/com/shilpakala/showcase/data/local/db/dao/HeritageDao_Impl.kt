package com.shilpakala.showcase.`data`.local.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shilpakala.showcase.`data`.local.entity.HeritageEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class HeritageDao_Impl(
  __db: RoomDatabase,
) : HeritageDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHeritageEntity: EntityInsertAdapter<HeritageEntity>

  private val __deleteAdapterOfHeritageEntity: EntityDeleteOrUpdateAdapter<HeritageEntity>

  private val __updateAdapterOfHeritageEntity: EntityDeleteOrUpdateAdapter<HeritageEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfHeritageEntity = object : EntityInsertAdapter<HeritageEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `heritage` (`id`,`title`,`style`,`narrative`,`imageUrl`,`isAiGenerated`,`language`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HeritageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.style)
        statement.bindText(4, entity.narrative)
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpImageUrl)
        }
        val _tmp: Int = if (entity.isAiGenerated) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindText(7, entity.language)
        statement.bindLong(8, entity.cachedAt)
      }
    }
    this.__deleteAdapterOfHeritageEntity = object : EntityDeleteOrUpdateAdapter<HeritageEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `heritage` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: HeritageEntity) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfHeritageEntity = object : EntityDeleteOrUpdateAdapter<HeritageEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `heritage` SET `id` = ?,`title` = ?,`style` = ?,`narrative` = ?,`imageUrl` = ?,`isAiGenerated` = ?,`language` = ?,`cachedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: HeritageEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.style)
        statement.bindText(4, entity.narrative)
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpImageUrl)
        }
        val _tmp: Int = if (entity.isAiGenerated) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindText(7, entity.language)
        statement.bindLong(8, entity.cachedAt)
        statement.bindText(9, entity.id)
      }
    }
  }

  public override suspend fun insertHeritage(heritage: HeritageEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfHeritageEntity.insertAndReturnId(_connection, heritage)
    _result
  }

  public override suspend fun insertHeritageList(heritageList: List<HeritageEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfHeritageEntity.insert(_connection, heritageList)
  }

  public override suspend fun deleteHeritage(heritage: HeritageEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfHeritageEntity.handle(_connection, heritage)
  }

  public override suspend fun updateHeritage(heritage: HeritageEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfHeritageEntity.handle(_connection, heritage)
  }

  public override suspend fun getHeritageById(id: String): HeritageEntity? {
    val _sql: String = "SELECT * FROM heritage WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfStyle: Int = getColumnIndexOrThrow(_stmt, "style")
        val _columnIndexOfNarrative: Int = getColumnIndexOrThrow(_stmt, "narrative")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsAiGenerated: Int = getColumnIndexOrThrow(_stmt, "isAiGenerated")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: HeritageEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpStyle: String
          _tmpStyle = _stmt.getText(_columnIndexOfStyle)
          val _tmpNarrative: String
          _tmpNarrative = _stmt.getText(_columnIndexOfNarrative)
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpIsAiGenerated: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAiGenerated).toInt()
          _tmpIsAiGenerated = _tmp != 0
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result = HeritageEntity(_tmpId,_tmpTitle,_tmpStyle,_tmpNarrative,_tmpImageUrl,_tmpIsAiGenerated,_tmpLanguage,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeHeritageById(id: String): Flow<HeritageEntity?> {
    val _sql: String = "SELECT * FROM heritage WHERE id = ?"
    return createFlow(__db, false, arrayOf("heritage")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfStyle: Int = getColumnIndexOrThrow(_stmt, "style")
        val _columnIndexOfNarrative: Int = getColumnIndexOrThrow(_stmt, "narrative")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsAiGenerated: Int = getColumnIndexOrThrow(_stmt, "isAiGenerated")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: HeritageEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpStyle: String
          _tmpStyle = _stmt.getText(_columnIndexOfStyle)
          val _tmpNarrative: String
          _tmpNarrative = _stmt.getText(_columnIndexOfNarrative)
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpIsAiGenerated: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAiGenerated).toInt()
          _tmpIsAiGenerated = _tmp != 0
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result = HeritageEntity(_tmpId,_tmpTitle,_tmpStyle,_tmpNarrative,_tmpImageUrl,_tmpIsAiGenerated,_tmpLanguage,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeHeritageByLanguage(language: String): Flow<List<HeritageEntity>> {
    val _sql: String = "SELECT * FROM heritage WHERE language = ? ORDER BY title ASC"
    return createFlow(__db, false, arrayOf("heritage")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, language)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfStyle: Int = getColumnIndexOrThrow(_stmt, "style")
        val _columnIndexOfNarrative: Int = getColumnIndexOrThrow(_stmt, "narrative")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsAiGenerated: Int = getColumnIndexOrThrow(_stmt, "isAiGenerated")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: MutableList<HeritageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HeritageEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpStyle: String
          _tmpStyle = _stmt.getText(_columnIndexOfStyle)
          val _tmpNarrative: String
          _tmpNarrative = _stmt.getText(_columnIndexOfNarrative)
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpIsAiGenerated: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAiGenerated).toInt()
          _tmpIsAiGenerated = _tmp != 0
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _item = HeritageEntity(_tmpId,_tmpTitle,_tmpStyle,_tmpNarrative,_tmpImageUrl,_tmpIsAiGenerated,_tmpLanguage,_tmpCachedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getHeritageByStyle(style: String, language: String): HeritageEntity? {
    val _sql: String = "SELECT * FROM heritage WHERE style = ? AND language = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, style)
        _argIndex = 2
        _stmt.bindText(_argIndex, language)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfStyle: Int = getColumnIndexOrThrow(_stmt, "style")
        val _columnIndexOfNarrative: Int = getColumnIndexOrThrow(_stmt, "narrative")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsAiGenerated: Int = getColumnIndexOrThrow(_stmt, "isAiGenerated")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: HeritageEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpStyle: String
          _tmpStyle = _stmt.getText(_columnIndexOfStyle)
          val _tmpNarrative: String
          _tmpNarrative = _stmt.getText(_columnIndexOfNarrative)
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpIsAiGenerated: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAiGenerated).toInt()
          _tmpIsAiGenerated = _tmp != 0
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result = HeritageEntity(_tmpId,_tmpTitle,_tmpStyle,_tmpNarrative,_tmpImageUrl,_tmpIsAiGenerated,_tmpLanguage,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeAllHeritage(): Flow<List<HeritageEntity>> {
    val _sql: String = "SELECT * FROM heritage ORDER BY cachedAt DESC"
    return createFlow(__db, false, arrayOf("heritage")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfStyle: Int = getColumnIndexOrThrow(_stmt, "style")
        val _columnIndexOfNarrative: Int = getColumnIndexOrThrow(_stmt, "narrative")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfIsAiGenerated: Int = getColumnIndexOrThrow(_stmt, "isAiGenerated")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cachedAt")
        val _result: MutableList<HeritageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HeritageEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpStyle: String
          _tmpStyle = _stmt.getText(_columnIndexOfStyle)
          val _tmpNarrative: String
          _tmpNarrative = _stmt.getText(_columnIndexOfNarrative)
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpIsAiGenerated: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAiGenerated).toInt()
          _tmpIsAiGenerated = _tmp != 0
          val _tmpLanguage: String
          _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _item = HeritageEntity(_tmpId,_tmpTitle,_tmpStyle,_tmpNarrative,_tmpImageUrl,_tmpIsAiGenerated,_tmpLanguage,_tmpCachedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCount(language: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM heritage WHERE language = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, language)
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

  public override suspend fun deleteOldCache(timestamp: Long) {
    val _sql: String = "DELETE FROM heritage WHERE cachedAt < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, timestamp)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM heritage"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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
