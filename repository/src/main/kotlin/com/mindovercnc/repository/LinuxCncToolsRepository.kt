package com.mindovercnc.repository

import com.mindovercnc.model.LinuxCncTool
import kotlinx.coroutines.flow.Flow

/**
 * This repository is designed to operate with the internal linuxcnc tool system that writes the tools in a tool.tbl file.
 * The internal tool library does not have any machine type specific features.
 */
interface LinuxCncToolsRepository {
    fun getTools(): Flow<List<LinuxCncTool>>

    fun addOrUpdateTool(latheTool: LinuxCncTool)

    fun removeTool(toolNo: Int)
}