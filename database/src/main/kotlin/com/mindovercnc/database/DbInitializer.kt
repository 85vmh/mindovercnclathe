package com.mindovercnc.database

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.entity.ToolHolderEntity
import com.mindovercnc.database.table.*
import com.mindovercnc.model.*
import java.sql.Connection
import kotlin.random.Random
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

object DbInitializer {
  private const val DB_NAME = "LatheTools.db"

  operator fun invoke() {
    Database.connect(url = "jdbc:sqlite:$DB_NAME", driver = "org.sqlite.JDBC")

    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
      addLogger(StdOutSqlLogger)
      SchemaUtils.create(
        CuttingInsertTable,
        WorkpieceMaterialTable,
        FeedsAndSpeedsTable,
        LatheToolTable,
        ToolHolderTable
      )
    }
    transaction {
      if (CuttingInsertEntity.count() == 0L) {
        createDummyInserts()
      }
      if (LatheToolEntity.count() == 0L) {
        createDummyTools()
      }
      if (ToolHolderEntity.count() == 0L) {
        createDummyHolders()
      }
    }
  }

  private fun createDummyHolders() {
    val types = ToolHolderType.values()
    repeat(5) {
      ToolHolderEntity.new {
        holderNumber = it + 1
        holderType = types.random()
        cutter = null
        clampingPosition = 0
        xOffset = Random.nextDouble()
        zOffset = Random.nextDouble()
      }
    }
  }

  private fun createDummyTools() {
    LatheToolEntity.new {
      insert = getInsertByCode("DCMT")
      type = ToolType.Turning
      tipOrientation = TipOrientation.Position2.orient
      frontAngle = 0.0
      backAngle = 0.0
      spindleDirection = SpindleDirection.Reverse
    }
    LatheToolEntity.new {
      insert = getInsertByCode("VCMT")
      type = ToolType.Turning
      tipOrientation = TipOrientation.Position2.orient
      frontAngle = 0.0
      backAngle = 0.0
      spindleDirection = SpindleDirection.Reverse
    }
    LatheToolEntity.new {
      insert = getInsertByCode("CCMT")
      type = ToolType.Boring
      tipOrientation = TipOrientation.Position3.orient
      frontAngle = 0.0
      backAngle = 0.0
      spindleDirection = SpindleDirection.Reverse
      minBoreDiameter = 20.0
      maxZDepth = 50.0
    }
    LatheToolEntity.new {
      insert = getInsertByCode("VCMT")
      tipOrientation = TipOrientation.Position7.orient
      spindleDirection = SpindleDirection.Reverse
      type = ToolType.Parting
      bladeWidth = 2.0
      maxXDepth = 20.0
    }
    LatheToolEntity.new {
      type = ToolType.Drilling
      tipOrientation = TipOrientation.Position7.orient
      spindleDirection = SpindleDirection.Reverse
      toolDiameter = 8.0
      maxZDepth = 80.0
    }
  }

  private fun getInsertByCode(code: String): CuttingInsertEntity {
    return CuttingInsertEntity.find { CuttingInsertTable.code eq code }.first()
  }

  private fun createDummyInserts() {
    CuttingInsertEntity.new {
      madeOf = MadeOf.Carbide
      code = "CCMT"
      tipRadius = 0.8
      tipAngle = 80.0
      size = 9.5
    }
    CuttingInsertEntity.new {
      madeOf = MadeOf.Carbide
      code = "DCMT"
      tipRadius = 0.8
      tipAngle = 55.0
      size = 9.5
    }
    CuttingInsertEntity.new {
      madeOf = MadeOf.Carbide
      code = "VCMT"
      tipRadius = 0.8
      tipAngle = 35.0
      size = 9.5
    }
    CuttingInsertEntity.new {
      madeOf = MadeOf.Carbide
      code = "TCMT"
      tipRadius = 0.8
      tipAngle = 60.0
      size = 13.0
    }
  }
}
