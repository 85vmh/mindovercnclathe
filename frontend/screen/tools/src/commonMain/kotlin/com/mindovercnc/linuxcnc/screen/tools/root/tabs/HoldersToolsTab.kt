package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.ToolHoldersContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteDialog

class HoldersToolsTab(private val component: HoldersToolsComponent) :
    ToolsTabItem(ToolsScreenModel.Config.Holders) {
    @Composable
    override fun Content(toolsComponent: ToolsComponent, modifier: Modifier) {
        val state by component.state.collectAsState()
        ToolHoldersContent(
            state = state,
            onDelete = component::requestDeleteToolHolder,
            onLoad = component::loadToolHolder,
            onHolderChanged = component::loadToolHolders,
            onMount = component::onMountTool,
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
