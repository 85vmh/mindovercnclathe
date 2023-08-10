package com.mindovercnc.linuxcnc.tools.local

import com.mindovercnc.database.entity.WorkpieceMaterialEntity
import com.mindovercnc.linuxcnc.tools.WorkpieceMaterialRepository
import com.mindovercnc.model.WorkpieceMaterial
import org.jetbrains.exposed.sql.transactions.transaction


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryLocal : WorkpieceMaterialRepository {

    override fun insert(wpMaterial: WorkpieceMaterial) {
        WorkpieceMaterialEntity.new { name = wpMaterial.name }
    }

    override fun findAll(): List<WorkpieceMaterial> {
        return transaction { WorkpieceMaterialEntity.all().map { it.toWorkpieceMaterial() } }
    }
}
