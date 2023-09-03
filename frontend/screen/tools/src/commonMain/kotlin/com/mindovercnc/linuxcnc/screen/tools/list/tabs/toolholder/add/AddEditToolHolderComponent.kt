package com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType

interface AddEditToolHolderComponent : AppScreenComponent<AddEditToolHolderState> {
    fun setHolderNumber(value: Int)
    fun setHolderType(value: ToolHolderType)
    fun setLatheTool(value: LatheTool)
    fun applyChanges()
}