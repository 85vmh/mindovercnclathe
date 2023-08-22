package com.mindovercnc.linuxcnc.screen.tools.root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool

interface ToolsComponent : AppScreenComponent<ToolsState> {
    val childSlot: Value<ChildSlot<*, ToolsTabItem>>

    fun selectTab(config: ToolsScreenModel.Config)
}
