package com.mindovercnc.database.entity

import com.mindovercnc.database.table.FeedsAndSpeedsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FeedsAndSpeedsEntity(id: EntityID<Int>) : IntEntity(id) {

    var insert by CuttingInsertEntity referencedOn FeedsAndSpeedsTable.insertId
    var material by WorkpieceMaterialEntity referencedOn FeedsAndSpeedsTable.materialId

    var cssMin by FeedsAndSpeedsTable.cssMin
    var cssMax by FeedsAndSpeedsTable.cssMax
    var feedMin by FeedsAndSpeedsTable.feedMin
    var feedMax by FeedsAndSpeedsTable.feedMax
    var docMin by FeedsAndSpeedsTable.docMin
    var docMax by FeedsAndSpeedsTable.docMax

    companion object : IntEntityClass<FeedsAndSpeedsEntity>(FeedsAndSpeedsTable)
}