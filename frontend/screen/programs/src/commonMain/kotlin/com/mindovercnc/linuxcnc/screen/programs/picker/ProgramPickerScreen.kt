package com.mindovercnc.linuxcnc.screen.programs.picker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.Programs
import com.mindovercnc.linuxcnc.screen.programs.picker.ui.ProgramPickerFab
import com.mindovercnc.linuxcnc.screen.programs.picker.ui.ProgramPickerScreenUi
import com.mindovercnc.linuxcnc.screen.rememberScreenModel

class ProgramPickerScreen : Programs("Programs") {

    @Composable
    override fun Fab() {
        val screenModel = rememberScreenModel<ProgramPickerScreenModel>()
        ProgramPickerFab(screenModel)
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ProgramPickerScreenModel>()
        ProgramPickerScreenUi(screenModel, Modifier.fillMaxSize())
    }
}
