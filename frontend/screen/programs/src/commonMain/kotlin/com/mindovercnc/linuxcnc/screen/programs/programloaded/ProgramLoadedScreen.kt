package com.mindovercnc.linuxcnc.screen.programs.programloaded

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.programs.Programs
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.ProgramLoadedScreenUi
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.ProgramLoadedTitle
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import okio.Path
import org.kodein.di.bindProvider

class ProgramLoadedScreen(private val file: Path) : Programs() {

    @Composable
    override fun Title() {
        ProgramLoadedTitle(file)
    }

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<ProgramLoadedScreenModel> { bindProvider { file } }

        IconButton(modifier = iconButtonModifier, onClick = screenModel::stopProgram) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
        }
        IconButton(modifier = iconButtonModifier, onClick = screenModel::runProgram) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ProgramLoadedScreenModel> { bindProvider { file } }
        ProgramLoadedScreenUi(screenModel, Modifier.fillMaxSize())
    }
}
