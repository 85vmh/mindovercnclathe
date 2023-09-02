package com.mindovercnc.linuxcnc.screen.tools.list

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ToolsTabItem

interface ToolsListComponent :
    AppScreenComponent<ToolsState> {
    val childSlot: Value<ChildSlot<*, ToolsTabItem>>

    fun selectTab(config: com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config)

    sealed interface Config : Parcelable {

        val tabTitle: String

        @Parcelize
        data object Holders : com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config {
            override val tabTitle: String
                get() = "Tool Holders"
        }

        @Parcelize
        data object Lathe : com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config {
            override val tabTitle: String
                get() = "Lathe Tools"
        }

        @Parcelize
        data object CuttingInserts :
            com.mindovercnc.linuxcnc.screen.tools.list.ToolsListComponent.Config {
            override val tabTitle: String = "Cutting Inserts"
        }
    }
}
