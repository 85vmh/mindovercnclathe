package com.mindovercnc.linuxcnc.screen.tools.root.child

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.TitledChild
import com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert.AddEditCuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.add.lathetool.AddEditLatheToolComponent
import com.mindovercnc.linuxcnc.screen.tools.add.toolholder.AddEditToolHolderComponent
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.AddEditItemActions
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolTabsView
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolsListFab
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsRootComponent

sealed interface ToolsChild : TitledChild {

    @Composable fun Fab(rootComponent: ToolsRootComponent, modifier: Modifier) {}

    @Composable fun RowScope.Actions(rootComponent: ToolsRootComponent) {}

    data class List(val component: ToolsListComponent) : ToolsChild {
        @Composable
        override fun Title(modifier: Modifier) {
            val childSlot by component.childSlot.subscribeAsState()
            ToolTabsView(
                modifier = Modifier.width(450.dp).height(48.dp),
                currentTab = childSlot.child!!.instance.config,
                onTabSelected = component::selectTab
            )
        }

        @Composable
        override fun Fab(rootComponent: ToolsRootComponent, modifier: Modifier) {
            val childSlot by component.childSlot.subscribeAsState()
            ToolsListFab(childSlot.child!!.instance, rootComponent, modifier)
        }
    }

    data class AddEditLatheTool(val component: AddEditLatheToolComponent) : ToolsChild {
        @Composable
        override fun Title(modifier: Modifier) {
            Text("Lathe Tool", modifier)
        }

        @Composable
        override fun RowScope.Actions(rootComponent: ToolsRootComponent) {
            AddEditItemActions(component,rootComponent)
        }
    }

    data class AddEditCuttingInsert(val component: AddEditCuttingInsertComponent) : ToolsChild {
        @Composable
        override fun Title(modifier: Modifier) {
            Text("Cutting Insert", modifier)
        }

        @Composable
        override fun RowScope.Actions(rootComponent: ToolsRootComponent) {
            AddEditItemActions(component,rootComponent)
        }
    }

    data class AddEditToolHolder(val component: AddEditToolHolderComponent) : ToolsChild {
        @Composable
        override fun Title(modifier: Modifier) {
            Text("Tool Holder", modifier)
        }

        @Composable
        override fun RowScope.Actions(rootComponent: ToolsRootComponent) {
            AddEditItemActions(component,rootComponent)
        }
    }
}
