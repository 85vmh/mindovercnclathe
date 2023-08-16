package com.mindovercnc.linuxcnc.tools.remote

import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.model.tool.ToolHolder
import mu.KotlinLogging

/** Implementation for [ToolHolderRepository]. */
// TODO implement a remote solution
class ToolHolderRepositoryRemote : ToolHolderRepository {

    init {
        LOG.warn { "Remote implementation is missing" }
    }

    override fun getToolHolders(): List<ToolHolder> {
        /* no-op */
        return emptyList()
    }

    override fun createToolHolder(toolHolder: ToolHolder) {
        /* no-op */
    }

    override fun updateToolHolder(toolHolder: ToolHolder) {
        /* no-op */
    }

    override fun deleteToolHolder(toolHolder: ToolHolder): Boolean {
        /* no-op */
        return false
    }

    companion object {
        private val LOG = KotlinLogging.logger("ToolHolderRepositoryImpl")
    }
}
