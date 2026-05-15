package com.shilpakala.showcase.`data`.local.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.shilpakala.showcase.`data`.local.entity.ShilpiEntity
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
public class ShilpiDao_Impl(
  __db: RoomDatabase,
) : ShilpiDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfShilpiEntity: EntityInsertAdapter<ShilpiEntity>

  private val __deleteAdapterOfShilpiEntity: EntityDeleteOrUpdateAdapter<ShilpiEntity>

  private val __updateAdapterOfShilpiEntity: EntityDeleteOrUpdateAdapter<ShilpiEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfShilpiEntity = object : EntityInsertAdapter<ShilpiEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `shilpis` (`id`,`name`,`village`,`district`,`specialisation`,`profilePhotoUri`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ShilpiEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.village)
        statement.bindText(4, entity.district)
        statement.bindText(5, entity.specialisation)
        val _tmpProfilePhotoUri: String? = entity.profilePhotoUri
        if (_tmpProfilePhotoUri == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpProfilePhotoUri)
        }
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.updatedAt)
      }
    }
    this.__deleteAdapterOfShilpiEntity = object : EntityDeleteOrUpdateAdapter<ShilpiEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `shilpis` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShilpiEntity) {
        statement.bindText(1, entity.id)
      }
    }
    this.__updateAdapterOfShilpiEntity = object : EntityDeleteOrUpdateAdapter<ShilpiEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `shilpis` SET `id` = ?,`name` = ?,`village` = ?,`district` = ?,`specialisation` = ?,`profilePhotoUri` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ShilpiEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.village)
        statement.bindText(4, entity.district)
        statement.bindText(5, entity.specialisation)
        val _tmpProfilePhotoUri: String? = entity.profilePhotoUri
        if (_tmpProfilePhotoUri == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpProfilePhotoUri)
        }
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.updatedAt)
        statement.bindText(9, entity.id)
      }
    }
  }

  public override suspend fun insertShilpi(shilpi: ShilpiEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfShilpiEntity.insertAndReturnId(_connection, shilpi)
    _result
  }

  public override suspend fun deleteShilpi(shilpi: ShilpiEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfShilpiEntity.handle(_connection, shilpi)
  }

  public override suspend fun updateShilpi(shilpi: ShilpiEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfShilpiEntity.handle(_connection, shilpi)
  }

  public override suspend fun getShilpiById(id: String): ShilpiEntity? {
    val _sql: String = "SELECT * FROM shilpis WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ShilpiEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeShilpiById(id: String): Flow<ShilpiEntity?> {
    val _sql: String = "SELECT * FROM shilpis WHERE id = ?"
    return createFlow(__db, false, arrayOf("shilpis")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ShilpiEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeAllShilpis(): Flow<List<ShilpiEntity>> {
    val _sql: String = "SELECT * FROM shilpis ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("shilpis")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ShilpiEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ShilpiEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun observeCurrentShilpi(): Flow<ShilpiEntity?> {
    val _sql: String = "SELECT * FROM shilpis ORDER BY createdAt DESC LIMIT 1"
    return createFlow(__db, false, arrayOf("shilpis")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ShilpiEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCurrentShilpi(): ShilpiEntity? {
    val _sql: String = "SELECT * FROM shilpis ORDER BY createdAt DESC LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ShilpiEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getShilpiCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM shilpis"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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

  public override suspend fun getLastShilpiNumber(): Int? {
    val _sql: String = "SELECT MAX(CAST(SUBSTR(id, LENGTH(id) - 3) AS INTEGER)) FROM shilpis"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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

  public override fun searchShilpis(query: String): Flow<List<ShilpiEntity>> {
    val _sql: String = "SELECT * FROM shilpis WHERE name LIKE '%' || ? || '%' OR specialisation LIKE '%' || ? || '%'"
    return createFlow(__db, false, arrayOf("shilpis")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<ShilpiEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ShilpiEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRandomShilpi(): ShilpiEntity? {
    val _sql: String = "SELECT * FROM shilpis ORDER BY RANDOM() LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfVillage: Int = getColumnIndexOrThrow(_stmt, "village")
        val _columnIndexOfDistrict: Int = getColumnIndexOrThrow(_stmt, "district")
        val _columnIndexOfSpecialisation: Int = getColumnIndexOrThrow(_stmt, "specialisation")
        val _columnIndexOfProfilePhotoUri: Int = getColumnIndexOrThrow(_stmt, "profilePhotoUri")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: ShilpiEntity?
        if (_stmt.step()) {
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpVillage: String
          _tmpVillage = _stmt.getText(_columnIndexOfVillage)
          val _tmpDistrict: String
          _tmpDistrict = _stmt.getText(_columnIndexOfDistrict)
          val _tmpSpecialisation: String
          _tmpSpecialisation = _stmt.getText(_columnIndexOfSpecialisation)
          val _tmpProfilePhotoUri: String?
          if (_stmt.isNull(_columnIndexOfProfilePhotoUri)) {
            _tmpProfilePhotoUri = null
          } else {
            _tmpProfilePhotoUri = _stmt.getText(_columnIndexOfProfilePhotoUri)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = ShilpiEntity(_tmpId,_tmpName,_tmpVillage,_tmpDistrict,_tmpSpecialisation,_tmpProfilePhotoUri,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
