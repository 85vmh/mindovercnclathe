package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

sealed class ToolsTabItem(val config: ToolsListComponent.Config) {

    @Composable
    abstract fun Content(
        rootComponent: ToolsRootComponent,
        toolsComponent: ToolsListComponent,
        modifier: Modifier
    )
}
