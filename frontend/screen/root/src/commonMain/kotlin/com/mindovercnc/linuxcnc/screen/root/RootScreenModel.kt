package com.mindovercnc.linuxcnc.screen.root

import com.mindovercnc.linuxcnc.screen.root.RootComponent.Config
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.domain.MachineUsableUseCase
import com.mindovercnc.linuxcnc.screen.BaseScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningScreenModel
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootScreenModel
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import com.mindovercnc.repository.IoStatusRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

class RootScreenModel(
    private val di: DI,
    componentContext: ComponentContext,
) :
    BaseScreenModel<RootState>(RootState(), componentContext),
    RootComponent,
    ComponentContext by componentContext {

    private val ioStatusRepository = di.direct.instance<IoStatusRepository>()
    private val machineUsableUseCase = di.direct.instance<MachineUsableUseCase>()

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Status,
            handleBackButton = false,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = _childStack

    override fun openTab(tab: Config) {
        navigation.bringToFront(tab)
    }

    init {
        machineUsableUseCase.machineUsableFlow
            .onEach { usable -> mutableState.update { it.copy(isBottomBarEnabled = usable) } }
            .launchIn(coroutineScope)

        ioStatusRepository.ioStatusFlow
            .map { it.tool_status!!.tool_in_spindle }
            .onEach { tool -> mutableState.update { it.copy(currentTool = tool) } }
            .launchIn(coroutineScope)
    }

    private fun createChild(
        config: Config,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.Conversational -> RootComponent.Child.Conversational()
            Config.Manual -> RootComponent.Child.Manual(manualComponent())
            Config.Programs -> RootComponent.Child.Programs(programsComponent())
            Config.Status -> RootComponent.Child.Status(statusComponent())
            Config.Tools -> RootComponent.Child.Tools(toolsComponent())
        }
    }

    private fun manualComponent(): ManualTurningComponent {
        return di.direct.instance<ManualTurningScreenModel>()
    }

    private fun programsComponent(): ProgramsRootComponent {
        return di.direct.instance<ProgramsRootScreenModel>()
    }

    private fun statusComponent(): StatusRootComponent {
        return di.direct.instance<StatusRootScreenModel>()
    }

    private fun toolsComponent(): ToolsComponent {
        return di.direct.instance<ToolsScreenModel>()
    }
}
