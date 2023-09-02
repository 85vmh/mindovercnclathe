package com.mindovercnc.linuxcnc.screen.manual.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent

interface ManualRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun openVirtualLimits()
    fun openSimpleCycles()
    fun openTurningSettings()

    sealed interface Child {
        class Turning(val component: ManualTurningComponent) : Child
        class VirtualLimits(val component: VirtualLimitsComponent) : Child
        class SimpleCycles(val component: SimpleCyclesComponent) : Child
        class TurningSettings(val component: TurningSettingsComponent) : Child
    }

    sealed interface Config : Parcelable {
        @Parcelize data object Turning : Config
        @Parcelize data object VirtualLimits : Config
        @Parcelize data object SimpleCycles : Config
        @Parcelize data object TurningSettings : Config
    }
}
