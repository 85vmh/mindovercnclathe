package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.linuxcnc.tools.model.ToolHolder

interface ToolHolderRepository {
    suspend fun getToolHolders(): List<ToolHolder>
    suspend fun createToolHolder(toolHolder: ToolHolder)
    suspend fun updateToolHolder(toolHolder: ToolHolder)
    suspend fun deleteToolHolder(toolHolder: ToolHolder): Boolean
}
