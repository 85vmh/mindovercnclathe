package com.mindovercnc.linuxcnc

import com.mindovercnc.model.WorkpieceMaterial
import com.mindovercnc.repository.WorkpieceMaterialRepository
import mu.KotlinLogging


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryImpl : WorkpieceMaterialRepository {


    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun insert(wpMaterial: WorkpieceMaterial) {
        /* no-op */
    }

    override fun findAll(): List<WorkpieceMaterial> {
        /* no-op */
        return emptyList()
    }

    companion object {
        private val LOG = KotlinLogging.logger("WorkpieceMaterialRepository")
    }
}
