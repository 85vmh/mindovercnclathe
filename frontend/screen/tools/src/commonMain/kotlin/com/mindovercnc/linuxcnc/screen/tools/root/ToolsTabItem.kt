package com.mindovercnc.linuxcnc.screen.tools.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.CuttingInsertsContent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.LatheToolsContent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ui.ToolHoldersContent
import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteDialog
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteDialog
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolHolderDeleteDialog

sealed class ToolsTabItem(val tabTitle: String) {

    @Composable abstract fun Content(component: ToolsComponent, modifier: Modifier)

    data object ToolHolders : ToolsTabItem("Tool Holders") {
        @Composable
        override fun Content(component: ToolsComponent, modifier: Modifier) {
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

    data object LatheTools : ToolsTabItem("Lathe Tools") {
        @Composable
        override fun Content(component: ToolsComponent, modifier: Modifier) {
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

    data object CuttingInserts : ToolsTabItem("Cutting Inserts") {
        @Composable
        override fun Content(component: ToolsComponent, modifier: Modifier) {
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
}
