package com.mindovercnc.vtk

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import com.mindovercnc.model.MachineLimits
import com.mindovercnc.model.PathElement
import com.mindovercnc.model.Point2D

data class VtkUiState(
    val machineLimits: MachineLimits? = null,
    val toolPosition: Point2D = Point2D(0.0, 0.0),
    val wcsPosition: Point2D = Point2D(0.0, 0.0),
    val pathElements: List<PathElement> = emptyList(),
)

@Composable
fun VtkView(
    state: VtkUiState,
    modifier: Modifier = Modifier
) {
    SwingPanel(
        modifier = modifier,
        factory = {
            LatheVtkPanel()
        },
        update = {
            it.setMachineLimits(state.machineLimits)
            it.setToolPosition(state.toolPosition)
            it.setWcsPosition(state.wcsPosition)
            it.setPathElements(state.pathElements)
            it.repaint() //TODO: double check this
        }
    )
}