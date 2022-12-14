package com.mindovercnc.linuxcnc

import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.model.WorkpieceMaterial
import com.mindovercnc.repository.WorkpieceMaterialRepository
import org.jetbrains.exposed.sql.transactions.transaction

fun WorkpieceMaterialEntity.toWorkpieceMaterial(): WorkpieceMaterial {
    return WorkpieceMaterial(
        id = id.value,
        name = name,
    )
}

class WorkpieceMaterialRepositoryImpl : WorkpieceMaterialRepository {

    override fun insert(wpMaterial: WorkpieceMaterial) {
        WorkpieceMaterialEntity.new {
            name = wpMaterial.name
        }
    }

    override fun findAll(): List<WorkpieceMaterial> {
        return transaction {
            WorkpieceMaterialEntity.all().map {
                it.toWorkpieceMaterial()
            }
        }
    }
}