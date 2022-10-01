package com.mindovercnc.repository

import com.mindovercnc.model.ToolHolder

interface ToolHolderRepository {
    fun getToolHolders(): List<ToolHolder>
    fun createToolHolder(toolHolder: ToolHolder)
    fun updateToolHolder(toolHolder: ToolHolder)
    fun deleteToolHolder(toolHolder: ToolHolder): Boolean
}