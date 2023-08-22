package com.mindovercnc.linuxcnc.screen.tools.root

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

interface ToolsComponent : AppScreenComponent<ToolsState> {
    fun loadToolHolders()
    fun loadLatheTools()
    fun loadCuttingInserts()
    fun selectTab(tab: ToolsTabItem)
    fun requestDeleteToolHolder(toolHolder: ToolHolder)
    fun cancelDeleteToolHolder()
    fun deleteToolHolder(toolHolder: ToolHolder)
    fun onMountTool(toolHolder: ToolHolder)
    fun loadToolHolder(toolHolder: ToolHolder)
    fun deleteCuttingInsert(insert: CuttingInsert)
    fun requestDeleteLatheTool(latheTool: LatheTool)
    fun cancelDeleteLatheTool()
    fun deleteLatheTool(latheTool: LatheTool)
    fun requestDeleteCuttingInsert(cuttingInsert: CuttingInsert)
    fun cancelDeleteCuttingInsert()
}
