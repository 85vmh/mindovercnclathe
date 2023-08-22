package com.mindovercnc.linuxcnc.screen.programs.programloaded.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.mindovercnc.linuxcnc.screen.programs.programloaded.ProgramLoadedComponent
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import editor.EditorView

@Composable
internal fun ProgramLoadedScreenUi(component: ProgramLoadedComponent) {
    val state by component.state.collectAsState()

    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
            Box {
                VisualTurning(
                    state = state.visualTurningState,
                    Modifier.fillMaxWidth()
                        .height(400.dp)
                        .onSizeChanged { component.setViewportSize(it) }
                        //                            .onPointerEvent(PointerEventType.Scroll) {
                        //
                        // screenModel.zoomBy(it.changes.first().scrollDelta.y)
                        //                            }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                component.translate(dragAmount)
                            }
                        }
                )
                Row(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        modifier = AppScreen.iconButtonModifier,
                        onClick = { component.zoomOut() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "",
                        )
                    }
                    IconButton(
                        modifier = AppScreen.iconButtonModifier,
                        onClick = { component.zoomIn() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "",
                        )
                    }
                }
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
        Column(modifier = Modifier.width(420.dp)) {
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
}
