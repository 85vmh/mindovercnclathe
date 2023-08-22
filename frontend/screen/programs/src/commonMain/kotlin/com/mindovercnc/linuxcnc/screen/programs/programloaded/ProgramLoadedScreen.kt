package com.mindovercnc.linuxcnc.screen.programs.programloaded

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.AppScreen
import com.mindovercnc.linuxcnc.screen.programs.Programs
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ui.*
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import editor.EditorView
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

        IconButton(modifier = iconButtonModifier, onClick = { screenModel.stopProgram() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
            )
        }
        IconButton(modifier = iconButtonModifier, onClick = { screenModel.runProgram() }) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ProgramLoadedScreenModel> { bindProvider { file } }
        ProgramLoadedScreenUi(screenModel)
    }
}
