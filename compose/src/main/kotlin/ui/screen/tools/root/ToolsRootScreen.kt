package ui.screen.tools.root

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import screen.composables.ToolTabsView
import screen.composables.ToolsTabItem
import ui.screen.tools.Tools
import ui.screen.tools.root.tabs.CuttingInsertsContent
import ui.screen.tools.root.tabs.LatheToolsContent
import ui.screen.tools.root.tabs.ToolHoldersContent
import ui.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreen
import ui.screen.tools.root.tabs.lathetool.AddEditLatheToolScreen
import ui.screen.tools.root.tabs.toolholder.AddEditHolderScreen

private val tabContentModifier = Modifier.fillMaxWidth()

class ToolsRootScreen : Tools() {

    @Composable
    override fun Title() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        ToolTabsView(
            modifier = Modifier.width(450.dp).height(48.dp),
            currentTabIndex = state.currentTabIndex,
            onTabSelected = screenModel::selectTabWithIndex
        )
    }

    @Composable
    override fun Fab() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (state.currentTabIndex) {
            0 -> ExtendedFloatingActionButton(
                text = { Text("New Holder") },
                onClick = {
                    navigator.push(AddEditHolderScreen {
                        screenModel.loadToolHolders()
                    })
                },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "",
                    )
                }
            )

            1 -> ExtendedFloatingActionButton(
                text = { Text("New Tool") },
                onClick = {
                    navigator.push(AddEditLatheToolScreen {
                        screenModel.loadLatheTools()
                    })
                },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "",
                    )
                }
            )

            2 -> ExtendedFloatingActionButton(
                text = { Text("New Insert") },
                onClick = {
                    navigator.push(AddEditCuttingInsertScreen {
                        screenModel.loadCuttingInserts()
                    })
                },
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "",
                    )
                }
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (ToolsTabItem.values()[state.currentTabIndex]) {
            ToolsTabItem.ToolHolders -> ToolHoldersContent(
                state = state,
                onDelete = screenModel::requestDeleteToolHolder,
                onLoad = screenModel::loadToolHolder,
                onHolderChanged = screenModel::loadToolHolders,
                onMount = screenModel::onMountTool,
                modifier = tabContentModifier
            )

            ToolsTabItem.LatheTools -> LatheToolsContent(
                state,
                onDelete = screenModel::requestDeleteLatheTool,
                onToolChanged = screenModel::loadLatheTools,
                modifier = tabContentModifier
            )

            ToolsTabItem.CuttingInserts -> CuttingInsertsContent(
                state,
                onDelete = screenModel::requestDeleteCuttingInsert,
                onInsertChanged = screenModel::loadCuttingInserts,
                modifier = tabContentModifier
            )
        }

        state.toolHolderDeleteModel?.let {
            ToolHolderDeleteDialog(
                deleteModel = it,
                deleteClick = screenModel::deleteToolHolder,
                abortClick = screenModel::cancelDeleteToolHolder
            )
        }
        state.latheToolDeleteModel?.let {
            LatheToolDeleteDialog(
                deleteModel = it,
                deleteClick = screenModel::deleteLatheTool,
                abortClick = screenModel::cancelDeleteLatheTool
            )
        }
        state.cuttingInsertDeleteModel?.let {
            CuttingInsertDeleteDialog(
                deleteModel = it,
                deleteClick = screenModel::deleteCuttingInsert,
                abortClick = screenModel::cancelDeleteCuttingInsert
            )
        }
    }
}