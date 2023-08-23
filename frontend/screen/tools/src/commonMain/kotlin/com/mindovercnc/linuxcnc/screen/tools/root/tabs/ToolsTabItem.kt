package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent

sealed class ToolsTabItem(val config: ToolsComponent.Config) {

    @Composable abstract fun Content(toolsComponent: ToolsComponent, modifier: Modifier)
}
