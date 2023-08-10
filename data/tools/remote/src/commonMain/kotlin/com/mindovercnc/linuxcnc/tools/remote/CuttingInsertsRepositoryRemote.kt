package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.CuttingInsertsRepository
import com.mindovercnc.model.CuttingInsert
import mu.KotlinLogging

/** Implementation for [CuttingInsertsRepository]. */
class CuttingInsertsRepositoryRemote : CuttingInsertsRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun insert(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    override fun update(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    override fun findAll(): List<CuttingInsert> {
        /* no-op */
        return emptyList()
    }

    override fun delete(cuttingInsert: CuttingInsert) {
        /* no-op */
    }

    companion object {
        private val LOG = KotlinLogging.logger("CuttingInsertsRepository")
    }
}