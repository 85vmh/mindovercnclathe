package com.mindovercnc.linuxcnc.screen.programs.root.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mindovercnc.linuxcnc.screen.programs.picker.ui.ProgramPickerScreenUi
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.ProgramLoadedScreenUi
import com.mindovercnc.linuxcnc.screen.programs.root.ProgramsRootComponent

@Composable
fun ProgramsRootScreenUi(component: ProgramsRootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()

    Children(childStack, modifier = modifier) {
        when (val child = it.instance) {
            is ProgramsRootComponent.Child.Picker -> {
                ProgramPickerScreenUi(child.component, Modifier.fillMaxSize())
            }
            is ProgramsRootComponent.Child.Loaded -> {
                ProgramLoadedScreenUi(child.component, Modifier.fillMaxSize())
            }
        }
    }
}
