package com.mindovercnc.linuxcnc.screen.tools.root

import cafe.adriel.voyager.core.model.StateScreenModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent.Config
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.CuttingInsertsToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.HoldersToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.LatheToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.CuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.CuttingInsertScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.LatheToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool.LatheToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsScreenModel

class ToolsScreenModel(
    private val toolsUseCase: ToolsUseCase,
    private val componentContext: ComponentContext
) :
    StateScreenModel<ToolsState>(ToolsState()),
    ToolsComponent,
    ComponentContext by componentContext {

    private val navigation = SlotNavigation<Config>()

    private val _childSlot =
        childSlot(
            source = navigation,
            initialConfiguration = { Config.Holders },
            childFactory = ::createTabItem
        )

    override val childSlot: Value<ChildSlot<*, ToolsTabItem>> = _childSlot

    private fun createTabItem(
        config: Config,
        componentContext: ComponentContext,
    ): ToolsTabItem {
        return when (config) {
            Config.CuttingInserts ->
                CuttingInsertsToolsTab(cuttingInsertsComponent(componentContext))
            Config.Holders -> HoldersToolsTab(holdersComponent(componentContext))
            Config.Lathe -> LatheToolsTab(latheComponent(componentContext))
        }
    }

    private fun latheComponent(componentContext: ComponentContext): LatheToolsComponent {
        return LatheToolsScreenModel(toolsUseCase, componentContext)
    }

    private fun cuttingInsertsComponent(
        componentContext: ComponentContext
    ): CuttingInsertComponent {
        return CuttingInsertScreenModel(toolsUseCase, componentContext)
    }

    private fun holdersComponent(componentContext: ComponentContext): HoldersToolsComponent {
        return HoldersToolsScreenModel(toolsUseCase, componentContext)
    }

    override fun selectTab(config: Config) {
        navigation.activate(config)
    }
}
