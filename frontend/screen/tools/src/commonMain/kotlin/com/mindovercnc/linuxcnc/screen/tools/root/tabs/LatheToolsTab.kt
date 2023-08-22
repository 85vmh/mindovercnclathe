package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.LatheToolsContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteDialog

data object LatheToolsTab : ToolsTabItem(ToolsScreenModel.Config.Lathe) {
    @Composable
    override fun Content(toolsComponent: ToolsComponent, modifier: Modifier) {
        val state by toolsComponent.state.collectAsState()
        LatheToolsContent(
            state,
            onDelete = toolsComponent::requestDeleteLatheTool,
            onToolChanged = toolsComponent::loadLatheTools,
            modifier = modifier
        )
        state.latheToolDeleteModel?.let { deleteModel ->
            LatheToolDeleteDialog(
                deleteModel = deleteModel,
                deleteClick = toolsComponent::deleteLatheTool,
                abortClick = toolsComponent::cancelDeleteLatheTool
            )
        }
    }
}
