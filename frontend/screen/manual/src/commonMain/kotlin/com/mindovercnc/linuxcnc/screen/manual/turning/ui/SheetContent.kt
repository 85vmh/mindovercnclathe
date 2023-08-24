package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.WcsOffset
import com.mindovercnc.model.WcsUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ManualTurningSheet(
    sheetState: ModalBottomSheetState,
    wcsUiModel: WcsUiModel,
    onOffsetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val wcsOffsets: List<WcsOffset> = wcsUiModel.wcsOffsets
    val selected = wcsUiModel.selected

    Column(modifier.padding(top = 16.dp).wrapContentHeight()) {
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
            onOffsetClick = {
                onOffsetClick.invoke(it)
                scope.launch {
                    delay(500)
                    sheetState.hide()
                }
            }
        )
    }
}
