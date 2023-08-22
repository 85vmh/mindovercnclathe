package com.mindovercnc.linuxcnc.screen.tools.root.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.CuttingInsertsContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteDialog

data object CuttingInsertsToolsTab :
    ToolsTabItem( ToolsScreenModel.Config.CuttingInserts) {
    @Composable
    override fun Content(toolsComponent: ToolsComponent, modifier: Modifier) {
        val state by toolsComponent.state.collectAsState()
        CuttingInsertsContent(
            state,
            onDelete = toolsComponent::requestDeleteCuttingInsert,
            onInsertChanged = toolsComponent::loadCuttingInserts,
            modifier = modifier
        )
        state.cuttingInsertDeleteModel?.let { deleteModel ->
            CuttingInsertDeleteDialog(
                deleteModel = deleteModel,
                deleteClick = toolsComponent::deleteCuttingInsert,
                abortClick = toolsComponent::cancelDeleteCuttingInsert
            )
        }
    }
}
