package com.mindovercnc.database.entity

import com.mindovercnc.database.table.CuttingInsertTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CuttingInsertEntity(id: EntityID<Int>) : IntEntity(id) {
    var madeOf by CuttingInsertTable.madeOf
    var code by CuttingInsertTable.code
    var tipRadius by CuttingInsertTable.tipRadius
    var tipAngle by CuttingInsertTable.tipAngle
    var size by CuttingInsertTable.size

    companion object : IntEntityClass<CuttingInsertEntity>(CuttingInsertTable)
}