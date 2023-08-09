package com.mindovercnc.database.entity

import com.mindovercnc.database.table.LatheToolTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LatheToolEntity(id: EntityID<Int>) : IntEntity(id) {
    var insert by CuttingInsertEntity optionalReferencedOn LatheToolTable.insertId
    var type by LatheToolTable.type
    var tipOrientation by LatheToolTable.tipOrientation
    var frontAngle by LatheToolTable.frontAngle
    var backAngle by LatheToolTable.backAngle
    var spindleDirection by LatheToolTable.spindleDirection
    var toolDiameter by LatheToolTable.toolDiameter
    var minBoreDiameter by LatheToolTable.minBoreDiameter
    var maxZDepth by LatheToolTable.maxZDepth
    var bladeWidth by LatheToolTable.bladeWidth
    var maxXDepth by LatheToolTable.maxXDepth
    var minThreadPitch by LatheToolTable.minThreadPitch
    var maxThreadPitch by LatheToolTable.maxThreadPitch

    companion object : IntEntityClass<LatheToolEntity>(LatheToolTable)
}