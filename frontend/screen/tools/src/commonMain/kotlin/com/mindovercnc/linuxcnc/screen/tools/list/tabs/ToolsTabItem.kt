package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class ToolsTabItem(val config: com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config) {

    @Composable abstract fun Content(toolsComponent: com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent, modifier: Modifier)
}
