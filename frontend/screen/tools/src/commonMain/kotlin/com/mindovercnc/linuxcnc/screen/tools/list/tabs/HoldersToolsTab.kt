package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui.ToolHoldersContent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolHolderDeleteDialog
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

class HoldersToolsTab(private val component: HoldersToolsComponent) :
    ToolsTabItem(ToolsListComponent.Config.Holders) {
    @Composable
    override fun Content(
        rootComponent: ToolsRootComponent,
        toolsComponent: ToolsListComponent,
        modifier: Modifier
    ) {
        val state by component.state.collectAsState()

        ToolHoldersContent(
            state = state,
            onDelete = component::requestDeleteToolHolder,
            onLoad = component::loadToolHolder,
            onMount = component::onMountTool,
            onEdit = { toolHolder -> rootComponent.editToolHolder(toolHolder) },
            modifier = modifier
        )

        state.toolHolderDeleteModel?.let { deleteModel ->
            ToolHolderDeleteDialog(
                deleteModel = deleteModel,
                deleteClick = component::deleteToolHolder,
                abortClick = component::cancelDeleteToolHolder
            )
        }
    }
}
