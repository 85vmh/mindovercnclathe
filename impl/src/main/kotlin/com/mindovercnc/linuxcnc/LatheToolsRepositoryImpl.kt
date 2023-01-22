package com.mindovercnc.linuxcnc

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.table.CuttingInsertTable
import com.mindovercnc.database.table.LatheToolTable
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.model.ToolType
import com.mindovercnc.repository.LatheToolsRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun CuttingInsertEntity.toCuttingInsert(): CuttingInsert {
  return CuttingInsert(
    id = id.value,
    madeOf = madeOf,
    code = code,
    tipRadius = tipRadius,
    tipAngle = tipAngle,
    size = size
  )
}

/** Implementation for [LatheToolsRepository]. */
class LatheToolsRepositoryImpl : LatheToolsRepository {

  override fun getLatheTools(): List<LatheTool> {
    return transaction { LatheToolEntity.all().mapNotNull { it.toLatheTool() } }
  }

  override fun createLatheTool(latheTool: LatheTool) {
    transaction {
      when (latheTool) {
        is LatheTool.Turning ->
          LatheToolEntity.new {
            insert = getInsertById(latheTool.insert.id!!)
            type = ToolType.Turning
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
          }
        is LatheTool.Boring ->
          LatheToolEntity.new {
            insert = getInsertById(latheTool.insert.id!!)
            type = ToolType.Boring
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            minBoreDiameter = latheTool.minBoreDiameter
            maxZDepth = latheTool.maxZDepth
          }
        is LatheTool.Drilling ->
          LatheToolEntity.new {
            type = ToolType.Drilling
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            toolDiameter = latheTool.toolDiameter
            maxZDepth = latheTool.maxZDepth
          }
        is LatheTool.Reaming ->
          LatheToolEntity.new {
            type = ToolType.Reaming
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            toolDiameter = latheTool.toolDiameter
            maxZDepth = latheTool.maxZDepth
          }
        is LatheTool.Parting ->
          LatheToolEntity.new {
            type = ToolType.Parting
            insert = getInsertById(latheTool.insert.id!!)
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            bladeWidth = latheTool.bladeWidth
            maxXDepth = latheTool.maxXDepth
          }
        is LatheTool.Grooving ->
          LatheToolEntity.new {
            type = ToolType.Grooving
            insert = getInsertById(latheTool.insert.id!!)
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            bladeWidth = latheTool.bladeWidth
            maxXDepth = latheTool.maxXDepth
          }
        is LatheTool.Slotting ->
          LatheToolEntity.new {
            type = ToolType.Slotting
            tipOrientation = latheTool.tipOrientation.orient
            spindleDirection = latheTool.spindleDirection
            bladeWidth = latheTool.bladeWidth
            maxZDepth = latheTool.maxZDepth
          }
        else -> Unit
      }
    }
  }

  private fun getInsertById(insertId: Int): CuttingInsertEntity {
    return transaction { CuttingInsertEntity.find { CuttingInsertTable.id eq insertId }.first() }
  }

  override fun updateLatheTool(latheTool: LatheTool) {
    transaction {
      LatheToolTable.update({ LatheToolTable.id eq latheTool.toolId }) {
        when (latheTool) {
          is LatheTool.Turning -> {
            it[type] = ToolType.Turning
            it[insertId] = latheTool.insert.id!!
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
          }
          is LatheTool.Boring -> {
            it[type] = ToolType.Boring
            it[insertId] = latheTool.insert.id!!
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[minBoreDiameter] = latheTool.minBoreDiameter
            it[maxZDepth] = latheTool.maxZDepth
          }
          is LatheTool.Drilling -> {
            it[type] = ToolType.Drilling
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[toolDiameter] = latheTool.toolDiameter
            it[maxZDepth] = latheTool.maxZDepth
          }
          is LatheTool.Reaming -> {
            it[type] = ToolType.Drilling
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[toolDiameter] = latheTool.toolDiameter
            it[maxZDepth] = latheTool.maxZDepth
          }
          is LatheTool.Parting -> {
            it[type] = ToolType.Parting
            it[insertId] = latheTool.insert.id!!
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[bladeWidth] = latheTool.bladeWidth
            it[maxXDepth] = latheTool.maxXDepth
          }
          is LatheTool.Grooving -> {
            it[type] = ToolType.Grooving
            it[insertId] = latheTool.insert.id!!
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[bladeWidth] = latheTool.bladeWidth
            it[maxXDepth] = latheTool.maxXDepth
          }
          is LatheTool.Slotting -> {
            it[type] = ToolType.Grooving
            it[tipOrientation] = latheTool.tipOrientation.orient
            it[spindleDirection] = latheTool.spindleDirection
            it[bladeWidth] = latheTool.bladeWidth
            it[maxZDepth] = latheTool.maxZDepth
          }
          else -> Unit
        }
      }
    }
  }

  override fun deleteLatheTool(latheTool: LatheTool): Boolean {
    return transaction { LatheToolTable.deleteWhere { LatheToolTable.id eq latheTool.toolId } != 0 }
  }

  private fun LatheToolEntity.toLatheTool(): LatheTool? {
    return when (type) {
      ToolType.Turning ->
        LatheTool.Turning(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          tipOrientation = TipOrientation.getOrientation(tipOrientation),
          frontAngle = frontAngle!!,
          backAngle = backAngle!!,
          spindleDirection = spindleDirection,
        )
      ToolType.Boring ->
        LatheTool.Boring(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          tipOrientation = TipOrientation.getOrientation(tipOrientation),
          frontAngle = frontAngle!!,
          backAngle = backAngle!!,
          spindleDirection = spindleDirection,
          minBoreDiameter = minBoreDiameter ?: 0.0,
          maxZDepth = maxZDepth ?: 0.0
        )
      ToolType.Drilling ->
        LatheTool.Drilling(
          toolId = id.value,
          insert = null,
          toolDiameter = toolDiameter!!,
          maxZDepth = maxZDepth ?: 0.0
        )
      ToolType.Reaming ->
        LatheTool.Reaming(
          toolId = id.value,
          insert = null,
          toolDiameter = toolDiameter!!,
          maxZDepth = maxZDepth ?: 0.0
        )
      ToolType.Parting ->
        LatheTool.Parting(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          bladeWidth = bladeWidth!!,
          maxXDepth = maxXDepth ?: 0.0
        )
      ToolType.Grooving ->
        LatheTool.Grooving(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          tipOrientation = TipOrientation.getOrientation(tipOrientation),
          spindleDirection = spindleDirection,
          bladeWidth = bladeWidth!!,
          maxXDepth = maxXDepth ?: 0.0
        )
      ToolType.OdThreading ->
        LatheTool.OdThreading(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          minPitch = minThreadPitch ?: 0.0,
          maxPitch = maxThreadPitch ?: 0.0
        )
      ToolType.IdThreading ->
        LatheTool.IdThreading(
          toolId = id.value,
          insert = insert!!.toCuttingInsert(),
          minPitch = minThreadPitch ?: 0.0,
          maxPitch = maxThreadPitch ?: 0.0
        )
      ToolType.Slotting ->
        LatheTool.Slotting(
          toolId = id.value,
          insert = null,
          bladeWidth = bladeWidth!!,
          maxZDepth = maxZDepth ?: 0.0
        )
      else -> null
    }
  }
}
