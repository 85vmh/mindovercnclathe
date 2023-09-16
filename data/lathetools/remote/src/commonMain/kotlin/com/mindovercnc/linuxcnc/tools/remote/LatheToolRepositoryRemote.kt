package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import mu.KotlinLogging

/** Implementation for [LatheToolRepository]. */
class LatheToolRepositoryRemote : LatheToolRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override suspend fun getLatheTools(): List<LatheTool> {
        /* no-op */
        return emptyList()
    }

    override suspend fun getUnmountedLatheTools(): List<LatheTool> {
        /* no-op */
        return emptyList()
    }

    override suspend fun createLatheTool(latheTool: LatheTool) {
        /* no-op */
    }

    override suspend fun updateLatheTool(latheTool: LatheTool) {
        /* no-op */
    }

    override suspend fun deleteLatheTool(latheTool: LatheTool): Boolean {
        /* no-op */
        return false
    }

    companion object {
        private val LOG = KotlinLogging.logger("LatheToolsRepository")
    }
}
