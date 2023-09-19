package com.mindovercnc.linuxcnc.screen.tools.add.toolholder

import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.add.AddEditItemComponent
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder
import com.mindovercnc.linuxcnc.tools.model.ToolHolderType

interface AddEditToolHolderComponent :
    AppScreenComponent<AddEditToolHolderState>, AddEditItemComponent<ToolHolder> {
    fun setHolderNumber(value: Int)
    fun setHolderType(value: ToolHolderType)
    fun setLatheTool(value: LatheTool)
}
