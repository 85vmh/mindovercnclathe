package com.mindovercnc.linuxcnc.screen.manual.turning

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.screen.manual.Manual
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningScreenUi
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.ManualTurningSheet
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.SimpleCyclesGrid
import com.mindovercnc.model.WcsUiModel
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesScreen
import com.mindovercnc.linuxcnc.screen.rememberScreenModel
import com.mindovercnc.model.SimpleCycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ManualTurningScreen : Manual("Manual Turning") {

    override val drawerEnabled: Boolean
        @Composable get() = true

    @Composable
    override fun ColumnScope.DrawerContent(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val items = remember { SimpleCycle.entries }

        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
            text = "Simple Cycles",
            style = MaterialTheme.typography.headlineSmall,
        )
        Divider(modifier = Modifier.fillMaxWidth().padding())

        SimpleCyclesGrid(
            items = items,
            onCycleSelected = {
                scope.launch {
                    drawerState.close()
                    navigator.push(SimpleCyclesScreen(it))
                }
            },
            contentPadding = PaddingValues(16.dp)
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun SheetContent(sheetState: ModalBottomSheetState) {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        val state by screenModel.state.collectAsState()
        val scope = rememberCoroutineScope()

        state.wcsUiModel?.let { wcs ->
            ManualTurningSheet(
                modifier = Modifier.fillMaxWidth(),
                sheetState = sheetState,
                wcsUiModel = wcs,
                onOffsetClick = {
                    screenModel.setActiveWcs(it)
                    scope.launch {
                        delay(500)
                        sheetState.hide()
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        val state by screenModel.state.collectAsState()
        val scope = rememberCoroutineScope()

        val iconColor =
            when {
                state.virtualLimitsUiModel != null -> Color.Green
                state.virtualLimitsAvailable.not() -> Color.LightGray
                else -> LocalContentColor.current
            }

        state.wcsUiModel?.let { uiModel ->
            WcsAction(uiModel, onClick = { scope.launch { sheetState.show() } }, iconButtonModifier)
        }
        IconButton(
            enabled = state.virtualLimitsAvailable,
            modifier = iconButtonModifier,
            onClick = { screenModel.setVirtualLimitsActive(state.virtualLimitsUiModel == null) }
        ) {
            Icon(
                tint = iconColor,
                imageVector = Icons.Default.Star,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        ManualTurningScreenUi(TODO(),screenModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WcsAction(uiModel: WcsUiModel, onClick: () -> Unit, modifier: Modifier = Modifier) {
    BadgedBox(
        badge = {
            Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                Text(fontSize = 14.sp, text = uiModel.activeOffset)
            }
        },
        modifier = modifier
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "",
            )
        }
    }
}
