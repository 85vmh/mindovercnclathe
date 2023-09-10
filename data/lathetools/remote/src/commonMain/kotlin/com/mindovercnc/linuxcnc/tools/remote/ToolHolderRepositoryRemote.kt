package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import mu.KotlinLogging

/** Implementation for [ToolHolderRepository]. */
// TODO implement a remote solution
class ToolHolderRepositoryRemote : ToolHolderRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override suspend fun getToolHolders(): List<ToolHolder> {
        /* no-op */
        return emptyList()
    }

    override suspend fun createToolHolder(toolHolder: ToolHolder) {
        /* no-op */
    }

    override suspend fun updateToolHolder(toolHolder: ToolHolder) {
        /* no-op */
    }

    override suspend fun deleteToolHolder(toolHolder: ToolHolder): Boolean {
        /* no-op */
        return false
    }

    companion object {
        private val LOG = KotlinLogging.logger("ToolHolderRepositoryImpl")
    }
}
