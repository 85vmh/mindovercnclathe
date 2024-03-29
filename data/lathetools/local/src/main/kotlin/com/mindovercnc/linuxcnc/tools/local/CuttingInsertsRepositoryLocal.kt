package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.entity.CuttingInsertEntity
import com.mindovercnc.database.table.CuttingInsertTable
import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/** Implementation for [CuttingInsertsRepository]. */
class CuttingInsertsRepositoryLocal : CuttingInsertsRepository {

    override suspend fun insert(cuttingInsert: CuttingInsert) {
        transaction {
            CuttingInsertEntity.new {
                madeOf = cuttingInsert.madeOf
                code = cuttingInsert.code
                tipRadius = cuttingInsert.tipRadius
                tipAngle = cuttingInsert.tipAngle
                size = cuttingInsert.size
            }
        }
    }

    override suspend fun update(cuttingInsert: CuttingInsert) {
        transaction {
            CuttingInsertTable.update({ CuttingInsertTable.id eq cuttingInsert.id }) {
                it[code] = cuttingInsert.code
                it[madeOf] = cuttingInsert.madeOf
                it[tipRadius] = cuttingInsert.tipRadius
                it[tipAngle] = cuttingInsert.tipAngle
                it[size] = cuttingInsert.size
            }
        }
    }

    override suspend fun findAll(): List<CuttingInsert> {
        return transaction {
            CuttingInsertEntity.all().map {
                it.toCuttingInsert()
            }
        }
    }

    override suspend fun delete(cuttingInsert: CuttingInsert) {
        return transaction {
            CuttingInsertTable.deleteWhere { CuttingInsertTable.id eq cuttingInsert.id } != 0
        }
    }
}