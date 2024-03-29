package com.mindovercnc.linuxcnc.screen.tools.list.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.CuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ui.CuttingInsertsContent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.CuttingInsertDeleteDialog
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

class CuttingInsertsToolsTab(private val component: CuttingInsertComponent) :
    ToolsTabItem(ToolsListComponent.Config.CuttingInserts) {
    @Composable
    override fun Content(
        rootComponent: ToolsRootComponent,
        toolsComponent: ToolsListComponent,
        modifier: Modifier
    ) {
        val state by component.state.collectAsState()
        CuttingInsertsContent(
            state,
            onDelete = component::requestDeleteCuttingInsert,
            onEdit = { cuttingInsert -> rootComponent.editCuttingInsert(cuttingInsert) },
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
