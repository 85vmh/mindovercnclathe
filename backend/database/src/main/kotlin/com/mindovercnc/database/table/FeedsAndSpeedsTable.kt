package com.mindovercnc.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * This will map a one-to-many relationship between an insert and its speeds&feeds for a specific material.
 */
object FeedsAndSpeedsTable : IntIdTable() {
    val insertId = reference("insertId", CuttingInsertTable, onDelete = ReferenceOption.CASCADE)
    val materialId = reference("materialId", WorkpieceMaterialTable, onDelete = ReferenceOption.CASCADE)
    val cssMin = double("cssMin")
    val cssMax = double("cssMax")
    val feedMin = double("feedMin")
    val feedMax = double("feedMax")
    val docMin = double("docMin")
    val docMax = double("docMax")

    init {
        uniqueIndex(insertId, materialId)
    }
}