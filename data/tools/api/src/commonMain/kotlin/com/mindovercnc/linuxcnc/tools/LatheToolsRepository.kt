package com.mindovercnc.linuxcnc.tools

import com.mindovercnc.linuxcnc.tools.model.LatheTool

interface LatheToolsRepository {
    fun getLatheTools(): List<LatheTool>

    fun getUnmountedLatheTools() : List<LatheTool>
    fun createLatheTool(latheTool: LatheTool)
    fun updateLatheTool(latheTool: LatheTool)
    fun deleteLatheTool(latheTool: LatheTool): Boolean
}