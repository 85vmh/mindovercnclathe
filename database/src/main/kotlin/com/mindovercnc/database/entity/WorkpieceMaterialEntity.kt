package com.mindovercnc.database.entity

import com.mindovercnc.database.table.WorkpieceMaterialTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WorkpieceMaterialEntity(id: EntityID<Int>) : IntEntity(id) {

    var name by WorkpieceMaterialTable.name

    companion object : IntEntityClass<WorkpieceMaterialEntity>(WorkpieceMaterialTable)
}