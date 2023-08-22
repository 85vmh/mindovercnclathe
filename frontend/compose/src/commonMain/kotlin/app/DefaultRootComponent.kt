package app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.manual.root.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.root.ManualTurningScreenModel
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootComponent
import com.mindovercnc.linuxcnc.screen.status.root.StatusRootScreenModel
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsComponent
import com.mindovercnc.linuxcnc.screen.tools.root.ToolsScreenModel
import org.kodein.di.DI
import org.kodein.di.instance

class DefaultRootComponent(
    private val di: DI,
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Status,
            handleBackButton = false,
            childFactory = ::createChild
        )

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = _childStack

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
        val component by di.instance<ManualTurningScreenModel>()
        return component
    }

    private fun programsComponent(): ProgramsRootComponent {
        val component by di.instance<ProgramsRootComponent>()
        return component
    }

    private fun statusComponent(): StatusRootComponent {
        val component by di.instance<StatusRootScreenModel>()
        return component
    }

    private fun toolsComponent(): ToolsComponent {
        val component by di.instance<ToolsScreenModel>()
        return component
    }

    private sealed interface Config : Parcelable {
        @Parcelize object Manual : Config
        @Parcelize object Conversational : Config
        @Parcelize object Programs : Config
        @Parcelize object Tools : Config
        @Parcelize object Status : Config
    }
}
