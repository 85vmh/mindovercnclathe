package com.mindovercnc.linuxcnc.screen.tools.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreen
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.AddEditLatheToolScreen
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.AddEditHolderScreen
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolTabsView

private val tabContentModifier = Modifier.fillMaxWidth()

class ToolsRootScreen : Tools() {

    @Composable
    override fun Title() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        ToolTabsView(
            modifier = Modifier.width(450.dp).height(48.dp),
            currentTab = state.currentTab,
            onTabSelected = screenModel::selectTab
        )
    }

    @Composable
    override fun Fab() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        ToolsFab(state, navigator, screenModel)
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(modifier = Modifier.fillMaxWidth()) {
            state.currentTab.Content(screenModel, tabContentModifier)
        }
    }
}

@Composable
private fun ToolsFab(state: ToolsState, navigator: Navigator, component: ToolsComponent) {
    when (state.currentTab) {
        ToolsTabItem.ToolHolders -> {
            ExtendedFloatingActionButton(
                text = { Text("New Holder") },
                onClick = { navigator.push(AddEditHolderScreen { component.loadToolHolders() }) },
                icon = { AddIcon() }
            )
        }
        ToolsTabItem.LatheTools -> {
            ExtendedFloatingActionButton(
                text = { Text("New Tool") },
                onClick = { navigator.push(AddEditLatheToolScreen { component.loadLatheTools() }) },
                icon = { AddIcon() }
            )
        }
        ToolsTabItem.CuttingInserts -> {
            ExtendedFloatingActionButton(
                text = { Text("New Insert") },
                onClick = {
                    navigator.push(AddEditCuttingInsertScreen { component.loadCuttingInserts() })
                },
                icon = { AddIcon() }
            )
        }
    }
}

@Composable
private fun AddIcon() {
    Icon(
        Icons.Default.Add,
        contentDescription = null,
    )
}
