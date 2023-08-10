package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.WorkpieceMaterialRepository
import com.mindovercnc.model.WorkpieceMaterial
import mu.KotlinLogging


/** Implementation for [WorkpieceMaterialRepository]. */
class WorkpieceMaterialRepositoryRemote : WorkpieceMaterialRepository {


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
