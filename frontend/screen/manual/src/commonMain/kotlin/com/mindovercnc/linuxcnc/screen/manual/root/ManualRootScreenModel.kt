package com.mindovercnc.linuxcnc.screen.manual.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Child
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent.Config
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningScreenModel
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsScreenModel
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsScreenModel
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

    override val childStack: Value<ChildStack<*, Child>> = _childStack

    override fun openVirtualLimits() {
        navigation.push(Config.VirtualLimits)
    }

    override fun openSimpleCycles() {
        navigation.push(Config.SimpleCycles)
    }

    override fun openTurningSettings() {
        navigation.push(Config.TurningSettings)
    }

    private fun createChild(
        config: Config,
        @Suppress("UNUSED_PARAMETER") componentContext: ComponentContext
    ): Child {
        return when (config) {
            Config.Turning -> Child.Turning(manualTurningComponent())
            Config.SimpleCycles -> Child.SimpleCycles(simpleCyclesComponent())
            Config.VirtualLimits -> Child.VirtualLimits(virtualLimitsComponent())
            Config.TurningSettings -> Child.TurningSettings(turningSettingsComponent())
        }
    }

    private fun manualTurningComponent(): ManualTurningComponent {
        return di.direct.instance<ManualTurningScreenModel>()
    }

    private fun simpleCyclesComponent(): SimpleCyclesComponent {
        return di.direct.instance<SimpleCyclesScreenModel>()
    }

    private fun virtualLimitsComponent(): VirtualLimitsComponent {
        return di.direct.instance<VirtualLimitsScreenModel>()
    }

    private fun turningSettingsComponent(): TurningSettingsComponent {
        return di.direct.instance<TurningSettingsScreenModel>()
    }
}
