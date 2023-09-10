package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.linuxcnc.tools.model.LatheTool

interface LatheToolsRepository {
    suspend fun getLatheTools(): List<LatheTool>
    suspend fun getUnmountedLatheTools(): List<LatheTool>
    suspend fun createLatheTool(latheTool: LatheTool)
    suspend fun updateLatheTool(latheTool: LatheTool)
    suspend fun deleteLatheTool(latheTool: LatheTool): Boolean
}
