package com.mindovercnc.linuxcnc.screen.manual.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.ui.TurningSettingsScreenUi
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.ui.VirtualLimitsScreenUi

@Composable
fun ManualRootScreenUi(component: ManualRootComponent, modifier: Modifier = Modifier) {
    Children(component.childStack, modifier) {
        when (val child = it.instance) {
            is ManualRootComponent.Child.Turning -> {
                ManualTurningScreenUi(component, child.component, modifier)
            }
            is ManualRootComponent.Child.SimpleCycles -> {
                SimpleCyclesScreenUi(child.component, modifier)
            }
            is ManualRootComponent.Child.VirtualLimits -> {
                VirtualLimitsScreenUi(child.component, modifier)
            }
            is ManualRootComponent.Child.TurningSettings -> {
                TurningSettingsScreenUi(child.component,modifier)
            }
        }
    }
}
