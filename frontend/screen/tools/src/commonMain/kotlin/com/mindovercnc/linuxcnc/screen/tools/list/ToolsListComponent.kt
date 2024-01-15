package com.mindovercnc.linuxcnc.screen.tools.list

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.AppScreenComponent
import com.mindovercnc.linuxcnc.screen.tools.list.tabs.ToolsTabItem
import kotlinx.serialization.Serializable

interface ToolsListComponent : AppScreenComponent<ToolsState> {
    val childSlot: Value<ChildSlot<*, ToolsTabItem>>

    fun selectTab(config: Config)

    @Serializable
    sealed interface Config {

        val tabTitle: String

        @Serializable
        data object Holders : Config {
            override val tabTitle: String
                get() = "Tool Holders"
        }

        @Serializable
        data object Lathe : Config {
            override val tabTitle: String
                get() = "Lathe Tools"
        }

        @Serializable
        data object CuttingInserts : Config {
            override val tabTitle: String = "Cutting Inserts"
        }
    }
}
