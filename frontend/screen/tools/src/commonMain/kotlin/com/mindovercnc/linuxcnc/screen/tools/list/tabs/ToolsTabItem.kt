package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsComponent

sealed class ToolsTabItem(val config: com.mindovercnc.linuxcnc.screen.tools.list.ToolsComponent.Config) {

    @Composable abstract fun Content(toolsComponent: com.mindovercnc.linuxcnc.screen.tools.list.ToolsComponent, modifier: Modifier)
}
