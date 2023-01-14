package ui.screen.manual.root

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WcsOffsetsDialog(
  wcsUiModel: WcsUiModel,
  onOffsetClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  AlertDialog(
    onDismissRequest = {},
    buttons = { WcsOffsets(wcsUiModel, onOffsetClick) },
    modifier = modifier
  )
}

@Composable
private fun WcsOffsets(
  wcsUiModel: WcsUiModel,
  onOffsetClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val wcsOffsets: List<WcsOffset> = wcsUiModel.wcsOffsets
  val selected = wcsUiModel.selected
  Column(modifier = modifier) {
    Text(
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center,
      text = "Workpiece Coordinate Systems",
      style = MaterialTheme.typography.headlineSmall,
      color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(24.dp))

    WcsOffsetsView(
      wcsOffsets = wcsOffsets,
      selected = selected,
      contentPadding = PaddingValues(8.dp),
      onOffsetClick = onOffsetClick
    )
  }
}

@Composable
@Preview
fun WcsOffsetsPreview() {
  val items =
    listOf(
      WcsOffset(coordinateSystem = "x", xOffset = 0.0, zOffset = 0.0),
      WcsOffset(coordinateSystem = "y", xOffset = 0.0, zOffset = 0.0),
      WcsOffset(coordinateSystem = "z", xOffset = 0.0, zOffset = 0.0)
    )
  val uiModel = WcsUiModel("x", items)
  WcsOffsets(uiModel, onOffsetClick = {})
}
