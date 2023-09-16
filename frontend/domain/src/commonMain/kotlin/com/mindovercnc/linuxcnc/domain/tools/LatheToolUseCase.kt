package com.mindovercnc.linuxcnc.domain.tools

import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool

class LatheToolUseCase(private val latheToolRepository: LatheToolRepository) {

    suspend fun getLatheTools(): List<LatheTool> = latheToolRepository.getLatheTools()

    suspend fun deleteLatheTool(tool: LatheTool) = latheToolRepository.deleteLatheTool(tool)

    suspend fun createLatheTool(latheTool: LatheTool) =
        latheToolRepository.createLatheTool(latheTool)

    suspend fun updateLatheTool(latheTool: LatheTool) =
        latheToolRepository.updateLatheTool(latheTool)
}
