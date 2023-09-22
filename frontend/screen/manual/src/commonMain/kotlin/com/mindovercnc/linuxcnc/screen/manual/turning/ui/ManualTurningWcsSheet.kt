package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualTurningWcsSheet(
    sheetState: SheetState,
    component: ManualTurningComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    Surface(color = MaterialTheme.colorScheme.surface, modifier = modifier) {
        val wcs = state.wcsUiModel
        if (wcs == null) LinearProgressIndicator(Modifier.fillMaxWidth())
        else {
            ManualTurningSheet(
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
                wcsUiModel = wcs,
                onOffsetClick = {
                    component.setActiveWcs(it)
                    component.setWcsSheetVisible(false)
                }
            )
        }
    }
}
