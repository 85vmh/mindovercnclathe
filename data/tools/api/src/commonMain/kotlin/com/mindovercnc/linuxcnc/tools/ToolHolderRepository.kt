package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.model.tool.ToolHolder

interface ToolHolderRepository {
    fun getToolHolders(): List<ToolHolder>
    fun createToolHolder(toolHolder: ToolHolder)
    fun updateToolHolder(toolHolder: ToolHolder)
    fun deleteToolHolder(toolHolder: ToolHolder): Boolean
}