package com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.add

import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType

data class AddEditToolHolderState(
    val holderNumber: Int? = null,
    val type: ToolHolderType = ToolHolderType.Generic,
    val latheTool: LatheTool? = null,
    val latheToolsList: List<LatheTool> = emptyList(),
    val unmountedLatheTools: List<LatheTool> = emptyList()
)