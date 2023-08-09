package com.mindovercnc.database.entity

import com.mindovercnc.database.table.ToolHolderTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ToolHolderEntity(id: EntityID<Int>) : IntEntity(id) {
    var holderNumber by ToolHolderTable.holderNumber
    var holderType by ToolHolderTable.holderType
    var cutter by LatheToolEntity optionalReferencedOn ToolHolderTable.cutterId
    var clampingPosition by ToolHolderTable.clampingPosition
    var xOffset by ToolHolderTable.xOffset
    var zOffset by ToolHolderTable.zOffset


    companion object : IntEntityClass<ToolHolderEntity>(ToolHolderTable)
}