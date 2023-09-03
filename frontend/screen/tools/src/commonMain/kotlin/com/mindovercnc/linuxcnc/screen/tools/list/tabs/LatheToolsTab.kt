package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.LatheToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui.LatheToolsContent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.LatheToolDeleteDialog
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

class LatheToolsTab(private val component: LatheToolsComponent) :
    ToolsTabItem(ToolsListComponent.Config.Lathe) {
    @Composable
    override fun Content(rootComponent: ToolsRootComponent, toolsComponent: ToolsListComponent, modifier: Modifier) {
        val state by component.state.collectAsState()
        LatheToolsContent(
            state,
            onDelete = component::requestDeleteLatheTool,
            onToolChanged = component::loadLatheTools,
            modifier = modifier
        )
        state.latheToolDeleteModel?.let { deleteModel ->
            LatheToolDeleteDialog(
                deleteModel = deleteModel,
                deleteClick = component::deleteLatheTool,
                abortClick = component::cancelDeleteLatheTool
            )
        }
    }
}
