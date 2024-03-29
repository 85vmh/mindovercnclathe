package com.mindovercnc.linuxcnc.screen.manual.root

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.TitledChild
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent
import com.mindovercnc.model.SimpleCycle
import kotlinx.serialization.Serializable

interface ManualRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun openVirtualLimits()

    fun openSimpleCycles(simpleCycle: SimpleCycle)

    fun openTurningSettings()

    fun openTaperSettings()

    fun navigateUp()

    sealed interface Child : TitledChild {
        class Turning(val component: ManualTurningComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Turning")
            }
        }

        class VirtualLimits(val component: VirtualLimitsComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Turning")
            }
        }

        class SimpleCycles(val component: SimpleCyclesComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Simple cycles")
            }
        }

        class TurningSettings(val component: TurningSettingsComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Turning settings")
            }
        }

        class TaperSettings(val component: TaperSettingsComponent) : Child {
            @Composable
            override fun Title(modifier: Modifier) {
                Text("Taper settings")
            }
        }
    }

    @Serializable
    sealed interface Config {
        @Serializable data object Turning : Config

        @Serializable data object VirtualLimits : Config

        @Serializable data class SimpleCycles(val simpleCycle: SimpleCycle) : Config

        @Serializable data object TurningSettings : Config

        @Serializable data object TaperSettings : Config
    }
}
