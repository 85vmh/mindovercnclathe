package com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType

interface AddEditToolHolderComponent : AppScreenComponent<AddEditToolHolderState> {
    fun setHolderNumber(value: Int)
    fun setHolderType(value: ToolHolderType)
    fun setLatheTool(value: LatheTool)
    fun applyChanges()
}
