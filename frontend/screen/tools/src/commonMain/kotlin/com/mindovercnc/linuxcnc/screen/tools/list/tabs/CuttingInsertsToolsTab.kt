package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.CuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui.CuttingInsertsContent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.CuttingInsertDeleteDialog

class CuttingInsertsToolsTab(private val component: CuttingInsertComponent) :
    com.mindovercnc.linuxcnc.screen.tools.list.tabs.ToolsTabItem(com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config.CuttingInserts) {
    @Composable
    override fun Content(toolsComponent: com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent, modifier: Modifier) {
        val state by component.state.collectAsState()
        CuttingInsertsContent(
            state,
            onDelete = component::requestDeleteCuttingInsert,
            onInsertChanged = component::loadCuttingInserts,
            modifier = modifier
        )
        state.cuttingInsertDeleteModel?.let { deleteModel ->
            CuttingInsertDeleteDialog(
                deleteModel = deleteModel,
                deleteClick = component::deleteCuttingInsert,
                abortClick = component::cancelDeleteCuttingInsert
            )
        }
    }
}
