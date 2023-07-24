package ui.screen.manual.root

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import di.rememberScreenModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screen.composables.InputDialogView
import screen.uimodel.SimpleCycle
import ui.screen.manual.Manual
import ui.screen.manual.simplecycles.SimpleCyclesScreen

class ManualTurningScreen : Manual("Manual Turning") {

    override val drawerEnabled: Boolean
        @Composable get() = true

    @Composable
    override fun ColumnScope.DrawerContent(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val items = remember { SimpleCycle.values() }

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
                })
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @Composable
    override fun Actions() {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        val state by screenModel.state.collectAsState()
        val scope = rememberCoroutineScope()

        val iconColor =
            when {
                state.virtualLimitsUiModel != null -> Color.Green
                state.virtualLimitsAvailable.not() -> Color.LightGray
                else -> LocalContentColor.current
            }

        state.wcsUiModel?.let {
            IconButton(modifier = iconButtonModifier, onClick = { scope.launch { sheetState.show() } }) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = MaterialTheme.colorScheme.secondary) {
                            Text(fontSize = 14.sp, text = it.activeOffset)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "",
                    )
                }
            }
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
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        ManualTurningContent(screenModel, state, navigator)

        state.numPadState?.let { numPadState ->
            InputDialogView(
                numPadState = numPadState,
                onCancel = { screenModel.closeNumPad() },
                onSubmit = {
                    numPadState.onSubmitAction(it)
                    screenModel.closeNumPad()
                }
            )
        }
    }
}
