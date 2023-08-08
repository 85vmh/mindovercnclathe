package com.mindovercnc.linuxcnc

import com.mindovercnc.model.LinuxCncTool
import com.mindovercnc.repository.LinuxCncToolsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging

/** Implementation for [LinuxCncToolsRepository]. */
class LinuxCncToolsRepositoryImpl : LinuxCncToolsRepository {

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
