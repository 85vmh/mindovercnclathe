package com.mindovercnc.linuxcnc

import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.repository.CuttingInsertsRepository
import mu.KotlinLogging

/** Implementation for [CuttingInsertsRepository]. */
class CuttingInsertsRepositoryImpl : CuttingInsertsRepository {

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