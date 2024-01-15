package com.mindovercnc.linuxcnc.screen.tools.root.child

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.TitledChild
import com.mindovercnc.linuxcnc.screen.tools.add.AddEditItemComponent
import com.mindovercnc.linuxcnc.screen.tools.add.cuttinginsert.AddEditCuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.add.feedspeed.AddEditFeedsAndSpeedsComponent
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

    data class AddEditLatheTool(
        override val component: AddEditLatheToolComponent,
    ) : AddEditItem()

    data class AddEditCuttingInsert(
        override val component: AddEditCuttingInsertComponent,
    ) : AddEditItem()

    data class AddEditToolHolder(
        override val component: AddEditToolHolderComponent,
    ) : AddEditItem()

    data class AddEditFeedsAndSpeeds(
        override val component: AddEditFeedsAndSpeedsComponent,
    ) : AddEditItem()

    abstract class AddEditItem : ToolsChild {
        abstract val component: AddEditItemComponent<*>

        @Composable
        override fun Title(modifier: Modifier) {
            Text(component.title, modifier)
        }

        @Composable
        override fun RowScope.Actions(rootComponent: ToolsRootComponent) {
            AddEditItemActions(component, rootComponent)
        }
    }
}
