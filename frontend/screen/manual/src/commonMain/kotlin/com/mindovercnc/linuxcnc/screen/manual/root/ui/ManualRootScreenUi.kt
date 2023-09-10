package com.mindovercnc.linuxcnc.screen.manual.root.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui.SimpleCyclesScreenUi
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.ui.TaperSettingsScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.ui.TurningSettingsScreenUi
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.ui.VirtualLimitsScreenUi
import scroll.draggableScroll

@Composable
fun ManualRootScreenUi(component: ManualRootComponent, modifier: Modifier = Modifier) {
    Children(component.childStack, modifier) {
        val childModifier = Modifier.fillMaxSize().draggableScroll()
        when (val child = it.instance) {
            is ManualRootComponent.Child.Turning -> {
                ManualTurningScreenUi(component, child.component, childModifier)
            }
            is ManualRootComponent.Child.SimpleCycles -> {
                SimpleCyclesScreenUi(child.component, childModifier)
            }
            is ManualRootComponent.Child.VirtualLimits -> {
                VirtualLimitsScreenUi(child.component, childModifier)
            }
            is ManualRootComponent.Child.TurningSettings -> {
                TurningSettingsScreenUi(child.component, childModifier)
            }
            is ManualRootComponent.Child.TaperSettings -> {
                TaperSettingsScreenUi(child.component,childModifier)
            }
        }
    }
}
