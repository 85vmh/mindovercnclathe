package com.mindovercnc.linuxcnc.screen.manual.root.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningActions

@Composable
fun RowScope.ManualRootActions(component: ManualRootComponent) {
    val childStack by component.childStack.subscribeAsState()
    when (val child = childStack.active.instance) {
        is ManualRootComponent.Child.SimpleCycles -> {}
        is ManualRootComponent.Child.Turning -> {
            ManualTurningActions(child.component)
        }
        is ManualRootComponent.Child.TurningSettings -> {}
        is ManualRootComponent.Child.VirtualLimits -> {}
    }
}
