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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.screen.tools.Tools
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.CuttingInsertsToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.HoldersToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.LatheToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.AddEditCuttingInsertScreen
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.AddEditLatheToolScreen
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.add.AddEditHolderScreen
import com.mindovercnc.linuxcnc.screen.tools.root.ui.ToolTabsView

private val tabContentModifier = Modifier.fillMaxWidth()

class ToolsRootScreen : Tools() {

    @Composable
    override fun Title() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val childSlot by screenModel.childSlot.subscribeAsState()

        ToolTabsView(
            modifier = Modifier.width(450.dp).height(48.dp),
            currentTab = childSlot.child!!.instance.config,
            onTabSelected = screenModel::selectTab
        )
    }

    @Composable
    override fun Fab() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val childSlot by screenModel.childSlot.subscribeAsState()

        ToolsFab(childSlot.child!!.instance, navigator, screenModel)
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ToolsScreenModel>()
        val tabStack by screenModel.childSlot.subscribeAsState()
        Column(modifier = Modifier.fillMaxWidth()) {
            tabStack.child!!.instance.Content(screenModel, tabContentModifier)
        }
    }
}

@Composable
private fun ToolsFab(
    currentTab: ToolsTabItem,
    navigator: Navigator,
    component: ToolsComponent,
    modifier: Modifier = Modifier
) {
    when (currentTab) {
        is HoldersToolsTab -> {
            ExtendedFloatingActionButton(
                text = { Text("New Holder") },
                onClick = {
                    navigator.push(
                        AddEditHolderScreen {
                            // TODO: component.loadToolHolders()
                        }
                    )
                },
                icon = { AddIcon() },
                modifier = modifier
            )
        }
        LatheToolsTab -> {
            ExtendedFloatingActionButton(
                text = { Text("New Tool") },
                onClick = { navigator.push(AddEditLatheToolScreen { component.loadLatheTools() }) },
                icon = { AddIcon() },
                modifier = modifier
            )
        }
        CuttingInsertsToolsTab -> {
            ExtendedFloatingActionButton(
                text = { Text("New Insert") },
                onClick = {
                    navigator.push(AddEditCuttingInsertScreen { component.loadCuttingInserts() })
                },
                icon = { AddIcon() },
                modifier = modifier
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
