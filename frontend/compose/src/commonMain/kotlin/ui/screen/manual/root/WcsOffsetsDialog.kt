package ui.screen.manual.root

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WcsOffsetsDialog(
    wcsUiModel: WcsUiModel,
    onOffsetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Workpiece Coordinate Systems",
                style = MaterialTheme.typography.headlineSmall,
                color = LocalContentColor.current
            )
        },
        text = { WcsOffsets(wcsUiModel, onOffsetClick) },
        confirmButton = {},
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
        Spacer(modifier = Modifier.height(24.dp))

        WcsOffsetsView(
            wcsOffsets = wcsOffsets,
            selected = selected,
            contentPadding = PaddingValues(8.dp),
            onOffsetClick = onOffsetClick
        )
    }
}
