package com.mindovercnc.linuxcnc.local

import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.model.WorkpieceMaterial
import com.mindovercnc.repository.WorkpieceMaterialRepository
import org.jetbrains.exposed.sql.transactions.transaction


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryImpl : WorkpieceMaterialRepository {

    override fun insert(wpMaterial: WorkpieceMaterial) {
        WorkpieceMaterialEntity.new { name = wpMaterial.name }
    }

    override fun findAll(): List<WorkpieceMaterial> {
        return transaction { WorkpieceMaterialEntity.all().map { it.toWorkpieceMaterial() } }
    }
}
