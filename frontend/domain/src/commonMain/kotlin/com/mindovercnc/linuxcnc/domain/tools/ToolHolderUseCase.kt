package com.mindovercnc.linuxcnc.domain.tools

import com.mindovercnc.linuxcnc.tools.LatheToolRepository
import com.mindovercnc.linuxcnc.tools.ToolHolderRepository
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType
import com.mindovercnc.model.TipOrientation

class ToolHolderUseCase
constructor(
    private val toolHolderRepository: ToolHolderRepository,
    private val latheToolRepository: LatheToolRepository,
) {

    suspend fun getToolHolders(): List<ToolHolder> = toolHolderRepository.getToolHolders()

    suspend fun createToolHolder(toolHolder: ToolHolder) =
        toolHolderRepository.createToolHolder(toolHolder)

    suspend fun updateToolHolder(toolHolder: ToolHolder) =
        toolHolderRepository.updateToolHolder(toolHolder)

    suspend fun deleteToolHolder(toolHolder: ToolHolder) =
        toolHolderRepository.deleteToolHolder(toolHolder)

    suspend fun getUnmountedLatheTools(holderType: ToolHolderType): List<LatheTool> {
        val allTools = latheToolRepository.getUnmountedLatheTools()
        val filteredTools =
            when (holderType) {
                ToolHolderType.Generic ->
                    allTools.filter {
                        it is LatheTool.Turning ||
                            it is LatheTool.Boring ||
                            it is LatheTool.Parting ||
                            it is LatheTool.Grooving ||
                            it is LatheTool.OdThreading ||
                            it is LatheTool.IdThreading ||
                            it is LatheTool.Slotting
                    }
                ToolHolderType.Centered ->
                    allTools.filter { it is LatheTool.Drilling || it is LatheTool.Reaming }
                ToolHolderType.Parting ->
                    allTools.filter {
                        it is LatheTool.Parting ||
                            (it is LatheTool.Grooving &&
                                it.tipOrientation == TipOrientation.Position6)
                    }
                ToolHolderType.Boring ->
                    allTools.filter {
                        it is LatheTool.Boring ||
                            (it is LatheTool.Grooving &&
                                it.tipOrientation == TipOrientation.Position8)
                    }
            }
        return filteredTools
    }
}
