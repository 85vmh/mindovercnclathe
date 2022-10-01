package com.mindovercnc.repository

import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolHolder

interface LatheToolsRepository {
    fun getLatheTools(): List<LatheTool>
    fun createLatheTool(latheTool: LatheTool)
    fun updateLatheTool(latheTool: LatheTool)
    fun deleteLatheTool(latheTool: LatheTool): Boolean
}