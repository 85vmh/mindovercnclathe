package com.mindovercnc.linuxcnc.screen.manual.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Child
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Config
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningScreenModel
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

class ManualRootScreenModel(
    private val di: DI,
    componentContext: ComponentContext,
) : ManualRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _childStack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Turning,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext
    ): Child {
        return when (config) {
            Config.Turning -> Child.Turning(manualTurningComponent())
        }
    }

    private fun manualTurningComponent(): ManualTurningComponent {
        return di.direct.instance<ManualTurningScreenModel>()
    }

    override val childStack: Value<ChildStack<*, Child>> = _childStack
}
