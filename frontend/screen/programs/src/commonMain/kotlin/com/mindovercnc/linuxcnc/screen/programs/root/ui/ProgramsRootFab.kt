package com.mindovercnc.linuxcnc.screen.programs.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.programs.picker.ui.ProgramPickerFab
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent

@Composable
fun ProgramsRootFab(component: ProgramsRootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()

    when (val child = childStack.active.instance) {
        is ProgramsRootComponent.Child.Loaded -> {
            /* no-op */
        }
        is ProgramsRootComponent.Child.Picker -> {
            ProgramPickerFab(component, child.component, modifier)
        }
    }
}
