package com.mindovercnc.linuxcnc.screen.tools.root

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.TitledChild
import com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.cuttinginsert.add.AddEditCuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.lathetool.add.AddEditLatheToolComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.toolholder.add.AddEditToolHolderComponent
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolTabsView
import com.mindovercnc.linuxcnc.screen.tools.list.ui.ToolsListFab
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import com.mindovercnc.linuxcnc.tools.model.ToolHolder

interface ToolsRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun addCuttingInsert()
    fun addLatheTool()
    fun addToolHolder()

    fun editCuttingInsert(cuttingInsert: CuttingInsert)
    fun editLatheTool(latheTool: LatheTool)
    fun editToolHolder(toolHolder: ToolHolder)

    fun navigateUp()

    sealed interface Child : TitledChild {

        @Composable fun Fab(rootComponent: ToolsRootComponent, modifier: Modifier) {}

        data class List(val component: ToolsListComponent) : Child {
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

        data class AddEditLatheTool(val component: AddEditLatheToolComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Lathe Tool", modifier)
            }
        }
        data class AddEditCuttingInsert(val component: AddEditCuttingInsertComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Cutting Insert", modifier)
            }
        }
        data class AddEditToolHolder(val component: AddEditToolHolderComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Tool Holder", modifier)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize data object List : Config
        @Parcelize data class AddEditLatheTool(val latheTool: LatheTool?) : Config
        @Parcelize data class AddEditCuttingInsert(val cuttingInsert: CuttingInsert?) : Config
        @Parcelize data class AddEditToolHolder(val toolHolder: ToolHolder?) : Config
    }
}
