package com.mindovercnc.linuxcnc

import com.mindovercnc.database.entity.LatheToolEntity
import com.mindovercnc.database.entity.ToolHolderEntity
import com.mindovercnc.database.table.ToolHolderTable
import com.mindovercnc.database.table.ToolHolderTable.holderNumber
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.model.ToolHolder
import com.mindovercnc.model.ToolType
import com.mindovercnc.repository.ToolHolderRepository
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


class ToolHolderRepositoryImpl : ToolHolderRepository {

    override fun getToolHolders(): List<ToolHolder> {
        return transaction {
            ToolHolderEntity.all().map {
                ToolHolder(
                    holderNumber = it.holderNumber,
                    type = it.holderType,
                    latheTool = it.cutter?.toLatheTool(),
                    xOffset = it.xOffset,
                    zOffset = it.zOffset
                )
            }
        }
    }

    private fun LatheToolEntity.toLatheTool(): LatheTool? {
        return when (type) {
            ToolType.Turning -> LatheTool.Turning(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                tipOrientation = TipOrientation.getOrientation(tipOrientation),
                frontAngle = frontAngle!!,
                backAngle = backAngle!!,
                spindleDirection = spindleDirection,
            )

            ToolType.Boring -> LatheTool.Boring(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                tipOrientation = TipOrientation.getOrientation(tipOrientation),
                frontAngle = frontAngle!!,
                backAngle = backAngle!!,
                spindleDirection = spindleDirection,
                minBoreDiameter = minBoreDiameter ?: 0.0,
                maxZDepth = maxZDepth ?: 0.0
            )

            ToolType.Drilling -> LatheTool.Drilling(
                toolId = id.value,
                insert = null,
                toolDiameter = toolDiameter!!,
                maxZDepth = maxZDepth ?: 0.0
            )

            ToolType.Reaming -> LatheTool.Reaming(
                toolId = id.value,
                insert = null,
                toolDiameter = toolDiameter!!,
                maxZDepth = maxZDepth ?: 0.0
            )

            ToolType.Parting -> LatheTool.Parting(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                bladeWidth = bladeWidth!!,
                maxXDepth = maxXDepth ?: 0.0
            )

            ToolType.Grooving -> LatheTool.Grooving(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                tipOrientation = TipOrientation.getOrientation(tipOrientation),
                spindleDirection = spindleDirection,
                bladeWidth = bladeWidth!!,
                maxXDepth = maxXDepth ?: 0.0
            )

            ToolType.OdThreading -> LatheTool.OdThreading(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                minPitch = minThreadPitch ?: 0.0,
                maxPitch = maxThreadPitch ?: 0.0
            )

            ToolType.IdThreading -> LatheTool.IdThreading(
                toolId = id.value,
                insert = insert!!.toCuttingInsert(),
                minPitch = minThreadPitch ?: 0.0,
                maxPitch = maxThreadPitch ?: 0.0
            )

            ToolType.Slotting -> LatheTool.Slotting(
                toolId = id.value,
                insert = null,
                bladeWidth = bladeWidth!!,
                maxZDepth = maxZDepth ?: 0.0
            )

            else -> null
        }
    }

    override fun createToolHolder(toolHolder: ToolHolder) {
        transaction {
            ToolHolderEntity.new {
                holderNumber = toolHolder.holderNumber
                holderType = toolHolder.type
                cutter = null
                clampingPosition = 0
                xOffset = toolHolder.xOffset
                zOffset = toolHolder.zOffset
            }
        }
    }

    override fun updateToolHolder(toolHolder: ToolHolder) {
        transaction {
            ToolHolderTable.update({ holderNumber eq toolHolder.holderNumber }) {
                it[holderType] = toolHolder.type
                it[cutterId] = toolHolder.latheTool?.toolId
                it[clampingPosition] = toolHolder.clampingPosition
                //offsets in a separate call
            }
        }
    }

    override fun deleteToolHolder(toolHolder: ToolHolder): Boolean {
        return transaction {
            ToolHolderTable.deleteWhere { holderNumber eq toolHolder.holderNumber } != 0
        }
    }
}