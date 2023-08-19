package com.mindovercnc.data.linuxcnc.remote

import com.mindovercnc.data.linuxcnc.LinuxCncToolsRepository
import com.mindovercnc.data.linuxcnc.model.LinuxCncTool
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging

/** Implementation for [LinuxCncToolsRepository]. */
class LinuxCncToolsRepositoryRemote : LinuxCncToolsRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun getTools(): Flow<List<LinuxCncTool>> {
        return emptyFlow()
    }

    override fun addOrUpdateTool(latheTool: LinuxCncTool) {
        /* no-op */
    }

    override fun removeTool(toolNo: Int) {
        /* no-op */
    }

    companion object {
        private val LOG = KotlinLogging.logger("LinuxCncToolsRepository")
    }

}
