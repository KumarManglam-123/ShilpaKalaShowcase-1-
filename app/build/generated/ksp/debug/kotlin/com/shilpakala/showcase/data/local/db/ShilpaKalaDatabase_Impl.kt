package com.shilpakala.showcase.`data`.local.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.FtsTableInfo
import androidx.room.util.TableInfo
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.shilpakala.showcase.`data`.local.db.dao.ArtworkDao
import com.shilpakala.showcase.`data`.local.db.dao.ArtworkDao_Impl
import com.shilpakala.showcase.`data`.local.db.dao.HeritageDao
import com.shilpakala.showcase.`data`.local.db.dao.HeritageDao_Impl
import com.shilpakala.showcase.`data`.local.db.dao.ShilpiDao
import com.shilpakala.showcase.`data`.local.db.dao.ShilpiDao_Impl
import com.shilpakala.showcase.`data`.local.db.dao.WipDao
import com.shilpakala.showcase.`data`.local.db.dao.WipDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass
import androidx.room.util.FtsTableInfo.Companion.read as ftsTableInfoRead
import androidx.room.util.TableInfo.Companion.read as tableInfoRead

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ShilpaKalaDatabase_Impl : ShilpaKalaDatabase() {
  private val _shilpiDao: Lazy<ShilpiDao> = lazy {
    ShilpiDao_Impl(this)
  }

  private val _artworkDao: Lazy<ArtworkDao> = lazy {
    ArtworkDao_Impl(this)
  }

  private val _heritageDao: Lazy<HeritageDao> = lazy {
    HeritageDao_Impl(this)
  }

  private val _wipDao: Lazy<WipDao> = lazy {
    WipDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "4ddf2f48072d325b74445474f0cb5731", "39e8e4a804efd6d9b584a4fd10b4d639") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `shilpis` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `village` TEXT NOT NULL, `district` TEXT NOT NULL, `specialisation` TEXT NOT NULL, `profilePhotoUri` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_shilpis_name` ON `shilpis` (`name`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_shilpis_specialisation` ON `shilpis` (`specialisation`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `artworks` (`id` TEXT NOT NULL, `shilpiId` TEXT NOT NULL, `title` TEXT NOT NULL, `material` TEXT NOT NULL, `dimensions` TEXT NOT NULL, `priceRange` TEXT NOT NULL, `status` TEXT NOT NULL, `imageUris` TEXT NOT NULL, `description` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`shilpiId`) REFERENCES `shilpis`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_artworks_shilpiId` ON `artworks` (`shilpiId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_artworks_material` ON `artworks` (`material`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_artworks_status` ON `artworks` (`status`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_artworks_createdAt` ON `artworks` (`createdAt`)")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `artwork_fts` USING FTS4(`title` TEXT NOT NULL, `material` TEXT NOT NULL, `description` TEXT NOT NULL, content=`artworks`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_BEFORE_UPDATE BEFORE UPDATE ON `artworks` BEGIN DELETE FROM `artwork_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_BEFORE_DELETE BEFORE DELETE ON `artworks` BEGIN DELETE FROM `artwork_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_AFTER_UPDATE AFTER UPDATE ON `artworks` BEGIN INSERT INTO `artwork_fts`(`docid`, `title`, `material`, `description`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`material`, NEW.`description`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_AFTER_INSERT AFTER INSERT ON `artworks` BEGIN INSERT INTO `artwork_fts`(`docid`, `title`, `material`, `description`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`material`, NEW.`description`); END")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `heritage` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `style` TEXT NOT NULL, `narrative` TEXT NOT NULL, `imageUrl` TEXT, `isAiGenerated` INTEGER NOT NULL, `language` TEXT NOT NULL, `cachedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_heritage_style` ON `heritage` (`style`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_heritage_language` ON `heritage` (`language`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `wip_stages` (`id` TEXT NOT NULL, `artworkId` TEXT NOT NULL, `stageName` TEXT NOT NULL, `photoUri` TEXT, `caption` TEXT NOT NULL, `sortOrder` INTEGER NOT NULL, `capturedAt` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`artworkId`) REFERENCES `artworks`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_wip_stages_artworkId` ON `wip_stages` (`artworkId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_wip_stages_sortOrder` ON `wip_stages` (`sortOrder`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4ddf2f48072d325b74445474f0cb5731')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `shilpis`")
        connection.execSQL("DROP TABLE IF EXISTS `artworks`")
        connection.execSQL("DROP TABLE IF EXISTS `artwork_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `heritage`")
        connection.execSQL("DROP TABLE IF EXISTS `wip_stages`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_BEFORE_UPDATE BEFORE UPDATE ON `artworks` BEGIN DELETE FROM `artwork_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_BEFORE_DELETE BEFORE DELETE ON `artworks` BEGIN DELETE FROM `artwork_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_AFTER_UPDATE AFTER UPDATE ON `artworks` BEGIN INSERT INTO `artwork_fts`(`docid`, `title`, `material`, `description`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`material`, NEW.`description`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_artwork_fts_AFTER_INSERT AFTER INSERT ON `artworks` BEGIN INSERT INTO `artwork_fts`(`docid`, `title`, `material`, `description`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`material`, NEW.`description`); END")
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsShilpis: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsShilpis.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("village", TableInfo.Column("village", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("district", TableInfo.Column("district", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("specialisation", TableInfo.Column("specialisation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("profilePhotoUri", TableInfo.Column("profilePhotoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsShilpis.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysShilpis: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesShilpis: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesShilpis.add(TableInfo.Index("index_shilpis_name", false, listOf("name"), listOf("ASC")))
        _indicesShilpis.add(TableInfo.Index("index_shilpis_specialisation", false, listOf("specialisation"), listOf("ASC")))
        val _infoShilpis: TableInfo = TableInfo("shilpis", _columnsShilpis, _foreignKeysShilpis, _indicesShilpis)
        val _existingShilpis: TableInfo = tableInfoRead(connection, "shilpis")
        if (!_infoShilpis.equals(_existingShilpis)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |shilpis(com.shilpakala.showcase.data.local.entity.ShilpiEntity).
              | Expected:
              |""".trimMargin() + _infoShilpis + """
              |
              | Found:
              |""".trimMargin() + _existingShilpis)
        }
        val _columnsArtworks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsArtworks.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("shilpiId", TableInfo.Column("shilpiId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("material", TableInfo.Column("material", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("dimensions", TableInfo.Column("dimensions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("priceRange", TableInfo.Column("priceRange", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("imageUris", TableInfo.Column("imageUris", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsArtworks.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysArtworks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysArtworks.add(TableInfo.ForeignKey("shilpis", "CASCADE", "NO ACTION", listOf("shilpiId"), listOf("id")))
        val _indicesArtworks: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesArtworks.add(TableInfo.Index("index_artworks_shilpiId", false, listOf("shilpiId"), listOf("ASC")))
        _indicesArtworks.add(TableInfo.Index("index_artworks_material", false, listOf("material"), listOf("ASC")))
        _indicesArtworks.add(TableInfo.Index("index_artworks_status", false, listOf("status"), listOf("ASC")))
        _indicesArtworks.add(TableInfo.Index("index_artworks_createdAt", false, listOf("createdAt"), listOf("ASC")))
        val _infoArtworks: TableInfo = TableInfo("artworks", _columnsArtworks, _foreignKeysArtworks, _indicesArtworks)
        val _existingArtworks: TableInfo = tableInfoRead(connection, "artworks")
        if (!_infoArtworks.equals(_existingArtworks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |artworks(com.shilpakala.showcase.data.local.entity.ArtworkEntity).
              | Expected:
              |""".trimMargin() + _infoArtworks + """
              |
              | Found:
              |""".trimMargin() + _existingArtworks)
        }
        val _columnsArtworkFts: MutableSet<String> = mutableSetOf()
        _columnsArtworkFts.add("title")
        _columnsArtworkFts.add("material")
        _columnsArtworkFts.add("description")
        val _infoArtworkFts: FtsTableInfo = FtsTableInfo("artwork_fts", _columnsArtworkFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `artwork_fts` USING FTS4(`title` TEXT NOT NULL, `material` TEXT NOT NULL, `description` TEXT NOT NULL, content=`artworks`)")
        val _existingArtworkFts: FtsTableInfo = ftsTableInfoRead(connection, "artwork_fts")
        if (!_infoArtworkFts.equals(_existingArtworkFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |artwork_fts(com.shilpakala.showcase.data.local.entity.ArtworkFts).
              | Expected:
              |""".trimMargin() + _infoArtworkFts + """
              |
              | Found:
              |""".trimMargin() + _existingArtworkFts)
        }
        val _columnsHeritage: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHeritage.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("style", TableInfo.Column("style", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("narrative", TableInfo.Column("narrative", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("isAiGenerated", TableInfo.Column("isAiGenerated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("language", TableInfo.Column("language", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHeritage.put("cachedAt", TableInfo.Column("cachedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHeritage: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesHeritage: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHeritage.add(TableInfo.Index("index_heritage_style", false, listOf("style"), listOf("ASC")))
        _indicesHeritage.add(TableInfo.Index("index_heritage_language", false, listOf("language"), listOf("ASC")))
        val _infoHeritage: TableInfo = TableInfo("heritage", _columnsHeritage, _foreignKeysHeritage, _indicesHeritage)
        val _existingHeritage: TableInfo = tableInfoRead(connection, "heritage")
        if (!_infoHeritage.equals(_existingHeritage)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |heritage(com.shilpakala.showcase.data.local.entity.HeritageEntity).
              | Expected:
              |""".trimMargin() + _infoHeritage + """
              |
              | Found:
              |""".trimMargin() + _existingHeritage)
        }
        val _columnsWipStages: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWipStages.put("id", TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("artworkId", TableInfo.Column("artworkId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("stageName", TableInfo.Column("stageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("photoUri", TableInfo.Column("photoUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("caption", TableInfo.Column("caption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("sortOrder", TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("capturedAt", TableInfo.Column("capturedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWipStages.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWipStages: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysWipStages.add(TableInfo.ForeignKey("artworks", "CASCADE", "NO ACTION", listOf("artworkId"), listOf("id")))
        val _indicesWipStages: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesWipStages.add(TableInfo.Index("index_wip_stages_artworkId", false, listOf("artworkId"), listOf("ASC")))
        _indicesWipStages.add(TableInfo.Index("index_wip_stages_sortOrder", false, listOf("sortOrder"), listOf("ASC")))
        val _infoWipStages: TableInfo = TableInfo("wip_stages", _columnsWipStages, _foreignKeysWipStages, _indicesWipStages)
        val _existingWipStages: TableInfo = tableInfoRead(connection, "wip_stages")
        if (!_infoWipStages.equals(_existingWipStages)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |wip_stages(com.shilpakala.showcase.data.local.entity.WipStageEntity).
              | Expected:
              |""".trimMargin() + _infoWipStages + """
              |
              | Found:
              |""".trimMargin() + _existingWipStages)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    _shadowTablesMap.put("artwork_fts", "artworks")
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "shilpis", "artworks", "artwork_fts", "heritage", "wip_stages")
  }

  public override fun clearAllTables() {
    super.performClear(true, "shilpis", "artworks", "artwork_fts", "heritage", "wip_stages")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ShilpiDao::class, ShilpiDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ArtworkDao::class, ArtworkDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HeritageDao::class, HeritageDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WipDao::class, WipDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun shilpiDao(): ShilpiDao = _shilpiDao.value

  public override fun artworkDao(): ArtworkDao = _artworkDao.value

  public override fun heritageDao(): HeritageDao = _heritageDao.value

  public override fun wipDao(): WipDao = _wipDao.value
}
