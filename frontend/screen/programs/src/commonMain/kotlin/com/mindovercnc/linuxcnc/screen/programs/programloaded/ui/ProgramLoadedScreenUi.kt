package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedState
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import com.mindovercnc.linuxcnc.widgets.ZoomControls
import editor.EditorView
import editor.FileNameHeader

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ProgramLoadedScreenUi(
    component: ProgramLoadedComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()

    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            Box {
                VisualTurning(
                    state = state.visualTurningState,
                    modifier =
                        Modifier.fillMaxWidth()
                            .height(400.dp)
                            .onSizeChanged { component.setViewportSize(it) }
                            .onPointerEvent(PointerEventType.Scroll) {
                                val change = it.changes.first()
                                val zoomIn = change.scrollDelta.y < 0
                                if (zoomIn) {
                                    component.zoomIn()
                                } else {
                                    component.zoomOut()
                                }
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    component.translate(dragAmount)
                                }
                            }
                )
                ZoomControls(
                    value = state.visualTurningState.scale,
                    onZoomIn = component::zoomIn,
                    onZoomOut = component::zoomOut,
                    zoomInEnabled = state.visualTurningState.canZoomIn,
                    zoomOutEnabled = state.visualTurningState.canZoomOut,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
                )
            }
            val editor = state.editor
            if (editor != null) {
                EditorView(
                    model = editor,
                    settings = state.settings,
                    showFileName = false,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
        }

        VerticalDivider(thickness = 1.dp)

        EndContent(state, component)
    }
}

@Composable
private fun EndContent(state: ProgramLoadedState, component: ProgramLoadedComponent) {
    Column(modifier = Modifier.width(420.dp)) {
        // file name
        state.editor?.let { editor ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
            ) {
                FileNameHeader(editor.file)
            }
        }
        state.toolChangeModel?.let {
            ToolChangeDialog(
                toolChangeModel = it,
                confirmationClick = component::confirmToolChanged,
                abortClick = component::cancelToolChange
            )
        }

        state.positionModel?.let {
            ProgramCoordinatesView(currentWcs = state.currentWcs, positionModel = it)
        }
        StatusView(
            machineStatus = state.machineStatus,
            modifier = Modifier.weight(1f).padding(8.dp)
        )
        ActiveCodesView(
            activeCodes = state.activeCodes,
            modifier = Modifier.fillMaxWidth().height(80.dp),
            onCodeClicked = component::onActiveCodeClicked
        )
    }
}
