package com.mindovercnc.database.table

import com.mindovercnc.model.ToolHolderType
import org.jetbrains.exposed.dao.id.IntIdTable

object ToolHolderTable : IntIdTable() {
    val holderNumber = integer("holder_number")
    val holderType = enumeration("holder_type", ToolHolderType::class)
    val cutterId = (integer("lathe_cutter_id").references(LatheToolTable.id)).nullable()
    val clampingPosition = integer("clamping_position")
    val xOffset = double("x_offset").nullable()
    val zOffset = double("z_offset").nullable()
}