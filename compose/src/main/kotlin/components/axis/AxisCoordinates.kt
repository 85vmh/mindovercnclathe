package components.axis

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.screen.manual.root.CoordinateUiModel

private val axisItemModifier = Modifier.fillMaxWidth().height(80.dp).padding(8.dp)

@Composable
fun AxisCoordinates(
  xCoordinate: CoordinateUiModel,
  zCoordinate: CoordinateUiModel,
  xToolOffsetsClicked: () -> Unit,
  zToolOffsetsClicked: () -> Unit,
  onZeroPosX: () -> Unit,
  onZeroPosZ: () -> Unit,
  onToggleAbsRelX: () -> Unit,
  onToggleAbsRelZ: () -> Unit,
  modifier: Modifier = Modifier,
  axisCoordinateHeight: Dp = 60.dp,
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(bottomEnd = 8.dp),
    border = BorderStroke(0.5.dp, SolidColor(Color.DarkGray)),
    color = MaterialTheme.colorScheme.surfaceVariant
  ) {
    Column {
      AxisCoordinate(
        xCoordinate,
        setHeight = axisCoordinateHeight,
        isDiameterMode = true,
        zeroPosClicked = onZeroPosX,
        absRelClicked = onToggleAbsRelX,
        toolOffsetsClicked = xToolOffsetsClicked,
        modifier = axisItemModifier,
      )
      AxisCoordinate(
        zCoordinate,
        setHeight = axisCoordinateHeight,
        zeroPosClicked = onZeroPosZ,
        absRelClicked = onToggleAbsRelZ,
        toolOffsetsClicked = zToolOffsetsClicked,
        modifier = axisItemModifier,
      )
    }
  }
}
