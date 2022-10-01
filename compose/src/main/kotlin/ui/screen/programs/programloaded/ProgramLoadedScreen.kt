package ui.screen.programs.programloaded

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import di.rememberScreenModel
import org.kodein.di.bindProvider
import screen.composables.VerticalDivider
import screen.composables.VisualTurning
import screen.composables.common.Settings
import screen.composables.editor.EditorView
import ui.screen.programs.Programs
import java.io.File

private fun File.displayableFilePath(): String {
    val elements = absolutePath.split("/")
    return if (elements.size > 2) {
        "../${elements.subList(elements.size - 2, elements.size).joinToString("/")}"
    } else {
        absolutePath
    }
}

class ProgramLoadedScreen(
    private val file: File
) : Programs() {

    @Composable
    override fun Title() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Program Loaded")
            Text(
                text = "[${file.displayableFilePath()}]",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    override fun Actions() {
        val screenModel = rememberScreenModel<ProgramLoadedScreenModel> {
            bindProvider { file }
        }
        val state by screenModel.state.collectAsState()

        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.stopProgram()
            }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
            )
        }
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.runProgram()
            }) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ProgramLoadedScreenModel> {
            bindProvider { file }
        }
        val settings = Settings()
        val state by screenModel.state.collectAsState()

        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box {
                    VisualTurning(
                        state = state.visualTurningState,
                        Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .onSizeChanged {
                                screenModel.setViewportSize(it)
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    screenModel.translate(dragAmount)
                                }
                            }
                    )
                    Row(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            modifier = iconButtonModifier,
                            onClick = {
                                screenModel.zoomOut()
                            }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "",
                            )
                        }
                        IconButton(
                            modifier = iconButtonModifier,
                            onClick = {
                                screenModel.zoomIn()
                            }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "",
                            )
                        }
                    }
                }
                EditorView(
                    model = state.editor,
                    settings = settings,
                    showFileName = false,
                    modifier = Modifier.fillMaxSize()
                )
            }
            VerticalDivider(thickness = 1.dp)
            Column(
                modifier = Modifier.width(420.dp)
            ) {
                state.toolChangeModel?.let {
                    ToolChangeDialog(
                        toolChangeModel = it,
                        confirmationClick = screenModel::confirmToolChanged,
                        abortClick = screenModel::cancelToolChange
                    )
                }

                state.positionModel?.let {
                    ProgramCoordinatesView(
                        currentWcs = state.currentWcs,
                        positionModel = it
                    )
                }
                StatusView(
                    machineStatus = state.machineStatus,
                    modifier = Modifier.weight(1f).padding(8.dp)
                )
                ActiveCodesView(
                    activeCodes = state.activeCodes,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    onCodeClicked = screenModel::onActiveCodeClicked
                )
            }
        }
    }
}
