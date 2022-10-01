package com.mindovercnc.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Plastics,
 * Aluminium,
 * LowCarbon Steel
 */
object WorkpieceMaterialTable : IntIdTable() {
    val name = varchar("name", 50)
}