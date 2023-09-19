package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import mu.KotlinLogging

/** Implementation for [CuttingInsertsRepository]. */
class CuttingInsertsRepositoryRemote : CuttingInsertsRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override suspend fun insert(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    override suspend fun update(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    override suspend fun findAll(): List<CuttingInsert> {
        /* no-op */
        return emptyList()
    }

    override suspend fun delete(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    companion object {
        private val LOG = KotlinLogging.logger("CuttingInsertsRepository")
    }
}
