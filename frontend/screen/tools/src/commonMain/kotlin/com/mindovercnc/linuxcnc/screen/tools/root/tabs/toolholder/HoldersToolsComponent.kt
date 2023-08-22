package com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

interface HoldersToolsComponent : AppScreenComponent<HoldersToolsState> {
    fun requestDeleteToolHolder(toolHolder: ToolHolder)
    fun cancelDeleteToolHolder()
    fun deleteToolHolder(toolHolder: ToolHolder)
    fun loadToolHolder(toolHolder: ToolHolder)
    fun loadToolHolders()
    fun onMountTool(toolHolder: ToolHolder)
}
