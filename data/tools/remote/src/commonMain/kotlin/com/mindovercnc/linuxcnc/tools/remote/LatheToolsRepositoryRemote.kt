package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.LatheToolsRepository
import com.mindovercnc.model.LatheTool
import mu.KotlinLogging

/** Implementation for [LatheToolsRepository]. */
class LatheToolsRepositoryRemote : LatheToolsRepository {

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
