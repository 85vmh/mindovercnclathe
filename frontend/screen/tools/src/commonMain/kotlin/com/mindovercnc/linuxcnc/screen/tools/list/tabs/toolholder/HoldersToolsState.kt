package com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder

import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolHolderDeleteModel
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

data class HoldersToolsState(
    val currentTool: Int = 0,
    val toolHolderDeleteModel: ToolHolderDeleteModel? = null,
    val toolHolders: List<ToolHolder> = emptyList(),
)
