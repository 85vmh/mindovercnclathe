package com.mindovercnc.linuxcnc.screen.tools.root

import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

data class ToolsState(
    val currentTab: ToolsTabItem = ToolsTabItem.ToolHolders,
    val toolHolders: List<ToolHolder> = emptyList(),
    val latheTools: List<LatheTool> = emptyList(),
    val cuttingInserts: List<CuttingInsert> = emptyList(),
    val currentTool: Int = 0,
    val toolHolderDeleteModel: ToolHolderDeleteModel? = null,
    val latheToolDeleteModel: LatheToolDeleteModel? = null,
    val cuttingInsertDeleteModel: CuttingInsertDeleteModel? = null,
)