package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.formatMaxDecimals

private val iconButtonModifier = Modifier.size(48.dp)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ZoomControls(
    value: Float,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    modifier: Modifier = Modifier,
    zoomInEnabled: Boolean = true,
    zoomOutEnabled: Boolean = true
) {
    var isHovered by remember { mutableStateOf(false) }
    Card(
        modifier =
            modifier
                .alpha(if (isHovered) 1f else 0.3f)
                .onPointerEvent(PointerEventType.Enter) { isHovered = true }
                .onPointerEvent(PointerEventType.Exit) { isHovered = false }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // zoom out
            IconButton(
                modifier = iconButtonModifier,
                onClick = onZoomOut,
                enabled = zoomOutEnabled,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                )
            }

            Text("${value.formatMaxDecimals(3)} x")

            // zoom in
            IconButton(
                modifier = iconButtonModifier,
                onClick = onZoomIn,
                enabled = zoomInEnabled,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        }
    }
}
