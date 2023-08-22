package com.mindovercnc.linuxcnc.screen.tools.root

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.domain.ToolsUseCase
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.CuttingInsertsToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.HoldersToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.LatheToolsTab
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.ToolsTabItem
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.toolholder.HoldersToolsScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.CuttingInsertDeleteModel
import com.mindovercnc.linuxcnc.screen.tools.root.ui.LatheToolDeleteModel
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.LatheTool
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

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
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext,
    ): ToolsTabItem {
        return when (config) {
            Config.CuttingInserts -> CuttingInsertsToolsTab
            Config.Holders -> HoldersToolsTab(holdersComponent())
            Config.Lathe -> LatheToolsTab
        }
    }

    private fun holdersComponent(): HoldersToolsComponent {
        return HoldersToolsScreenModel(toolsUseCase, componentContext)
    }

    init {
        loadLatheTools()
        loadCuttingInserts()
    }

    override fun loadLatheTools() {
        toolsUseCase
            .getLatheTools()
            .onEach { latheTools ->
                mutableState.update {
                    it.copy(
                        latheTools = latheTools,
                    )
                }
            }
            .launchIn(coroutineScope)
    }

    override fun loadCuttingInserts() {
        toolsUseCase
            .getCuttingInserts()
            .onEach { insertsList -> mutableState.update { it.copy(cuttingInserts = insertsList) } }
            .launchIn(coroutineScope)
    }

    override fun selectTab(config: Config) {
        navigation.activate(config)
    }

    override fun deleteCuttingInsert(insert: CuttingInsert) {
        toolsUseCase.deleteCuttingInsert(insert)
        cancelDeleteCuttingInsert()
        loadCuttingInserts()
    }

    override fun requestDeleteLatheTool(latheTool: LatheTool) {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = LatheToolDeleteModel(latheTool),
            )
        }
    }

    override fun cancelDeleteLatheTool() {
        mutableState.update {
            it.copy(
                latheToolDeleteModel = null,
            )
        }
    }

    override fun deleteLatheTool(latheTool: LatheTool) {
        toolsUseCase.deleteLatheTool(latheTool)
        cancelDeleteLatheTool()
        loadLatheTools()
    }

    override fun requestDeleteCuttingInsert(cuttingInsert: CuttingInsert) {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = CuttingInsertDeleteModel(cuttingInsert),
            )
        }
    }

    override fun cancelDeleteCuttingInsert() {
        mutableState.update {
            it.copy(
                cuttingInsertDeleteModel = null,
            )
        }
    }

    sealed interface Config : Parcelable {

        val tabTitle: String

        @Parcelize
        data object Holders : Config {
            override val tabTitle: String
                get() = "Tool Holders"
        }

        @Parcelize
        data object Lathe : Config {
            override val tabTitle: String
                get() = "Lathe Tools"
        }

        @Parcelize
        data object CuttingInserts : Config {
            override val tabTitle: String = "Cutting Inserts"
        }
    }
}
