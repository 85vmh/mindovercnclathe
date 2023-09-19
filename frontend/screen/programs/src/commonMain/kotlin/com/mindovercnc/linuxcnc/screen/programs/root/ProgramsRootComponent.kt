package com.mindovercnc.linuxcnc.screen.programs.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.mindovercnc.linuxcnc.screen.programs.picker.ProgramPickerComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import okio.Path

interface ProgramsRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateUp()
    fun openProgram(path: Path)

    sealed interface Child {
        data class Picker(val component: ProgramPickerComponent) : Child
        data class Loaded(val component: ProgramLoadedComponent) : Child
    }
}
