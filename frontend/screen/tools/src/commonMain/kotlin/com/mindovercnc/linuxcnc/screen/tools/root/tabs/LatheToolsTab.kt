package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.LatheToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.LatheToolsContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteDialog

class LatheToolsTab(private val component: LatheToolsComponent) :
    ToolsTabItem(ToolsScreenModel.Config.Lathe) {
    @Composable
    override fun Content(toolsComponent: ToolsComponent, modifier: Modifier) {
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
