package com.mindovercnc.linuxcnc.screen.manual.root.ui.axis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.CoordinatesUiData
import com.mindovercnc.model.CoordinateAxis

private val axisItemModifier = Modifier.fillMaxWidth().height(80.dp).padding(8.dp)

@Composable
fun AxisCoordinates(
    uiData: CoordinatesUiData,
    xToolOffsetsClicked: () -> Unit,
    zToolOffsetsClicked: () -> Unit,
    onZeroPosX: () -> Unit,
    onZeroPosZ: () -> Unit,
    onToggleAbsRelX: () -> Unit,
    onToggleAbsRelZ: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(bottomEnd = 8.dp),
        border = BorderStroke(0.5.dp, SolidColor(Color.DarkGray)),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column {
            uiData.x?.let { xCoordinate ->
                AxisCoordinate(
                    CoordinateAxis.X,
                    uiModel = xCoordinate,
                    isDiameterMode = true,
                    zeroPosClicked = onZeroPosX,
                    absRelClicked = onToggleAbsRelX,
                    toolOffsetsClicked = xToolOffsetsClicked,
                    modifier = axisItemModifier,
                )
            }
            //      uiData.y?.let {
            //        AxisCoordinate(
            //          CoordinateAxis.Y,
            //          zeroPosClicked = onZeroPosZ,
            //          absRelClicked = onToggleAbsRelZ,
            //          toolOffsetsClicked = zToolOffsetsClicked,
            //          modifier = axisItemModifier,
            //        )
            //      }
            uiData.z?.let { zCoordinate ->
                AxisCoordinate(
                    CoordinateAxis.Z,
                    uiModel = zCoordinate,
                    zeroPosClicked = onZeroPosZ,
                    absRelClicked = onToggleAbsRelZ,
                    toolOffsetsClicked = zToolOffsetsClicked,
                    modifier = axisItemModifier,
                )
            }
        }
    }
}
