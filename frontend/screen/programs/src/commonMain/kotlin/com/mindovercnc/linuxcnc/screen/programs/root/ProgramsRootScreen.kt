package com.mindovercnc.linuxcnc.screen.programs.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.Programs
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootFab
import com.mindovercnc.linuxcnc.screen.programs.root.ui.ProgramsRootScreenUi
import com.mindovercnc.linuxcnc.screen.rememberScreenModel

class ProgramsRootScreen : Programs("Programs") {

    @Composable
    override fun Content() {
        val component = rememberScreenModel<ProgramsRootScreenModel>()
        ProgramsRootScreenUi(component, Modifier.fillMaxSize())
    }

    @Composable
    override fun Fab() {
        val component = rememberScreenModel<ProgramsRootScreenModel>()
        ProgramsRootFab(component)
    }
}
