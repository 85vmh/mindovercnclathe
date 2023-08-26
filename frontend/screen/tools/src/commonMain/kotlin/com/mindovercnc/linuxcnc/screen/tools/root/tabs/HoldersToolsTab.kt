package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.add.AddEditHolderScreen
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.ToolHoldersContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteDialog

class HoldersToolsTab(private val component: HoldersToolsComponent) :
    ToolsTabItem(ToolsComponent.Config.Holders) {
    @Composable
    override fun Content(toolsComponent: ToolsComponent, modifier: Modifier) {
        val navigator = LocalNavigator.current
        val state by component.state.collectAsState()

        ToolHoldersContent(
            state = state,
            onDelete = component::requestDeleteToolHolder,
            onLoad = component::loadToolHolder,
            onMount = component::onMountTool,
            onEdit = { navigator?.push(AddEditHolderScreen(it, component::loadToolHolders)) },
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
