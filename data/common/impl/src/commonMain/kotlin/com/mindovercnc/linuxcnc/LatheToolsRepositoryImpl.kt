package com.mindovercnc.linuxcnc

import com.mindovercnc.model.LatheTool
import com.mindovercnc.repository.LatheToolsRepository
import mu.KotlinLogging

/** Implementation for [LatheToolsRepository]. */
class LatheToolsRepositoryImpl : LatheToolsRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun getLatheTools(): List<LatheTool> {
        /* no-op */
        return emptyList()
    }

    override fun getUnmountedLatheTools(): List<LatheTool> {
        /* no-op */
        return emptyList()
    }

    override fun createLatheTool(latheTool: LatheTool) {
        /* no-op */
    }

    override fun updateLatheTool(latheTool: LatheTool) {
        /* no-op */
    }

    override fun deleteLatheTool(latheTool: LatheTool): Boolean {
        /* no-op */
        return false
    }

    companion object {
        private val LOG = KotlinLogging.logger("LatheToolsRepository")
    }
}
