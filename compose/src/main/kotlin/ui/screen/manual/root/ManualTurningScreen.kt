package ui.screen.manual.root

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screen.composables.AxisCoordinate
import screen.composables.InputDialogView
import screen.composables.VerticalDivider
import screen.uimodel.InputType
import screen.uimodel.SimpleCycle
import ui.screen.manual.Manual
import ui.screen.manual.simplecycles.SimpleCyclesScreen
import ui.screen.manual.tapersettings.TaperSettingsScreen
import ui.screen.manual.turningsettings.TurningSettingsScreen
import ui.screen.manual.virtuallimits.VirtualLimitsScreen

private val axisItemModifier = Modifier.fillMaxWidth().height(80.dp).padding(8.dp)

class ManualTurningScreen : Manual("Manual Turning") {

    @OptIn(ExperimentalMaterialApi::class)
    override val drawerEnabled: Boolean
        get() = !sheetState.isVisible

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun DrawerContent(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val items = remember { SimpleCycle.values() }

        Column(
            modifier =
            Modifier.fillMaxHeight()
                .width(300.dp)
                .background(Color.White, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                text = "Simple Cycles",
                style = MaterialTheme.typography.headlineSmall,
            )
            Divider(modifier = Modifier.fillMaxWidth())
            SimpleCyclesList(
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
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun SheetContent(sheetState: ModalBottomSheetState) {
        val screenModel = rememberScreenModel<ManualTurningScreenModel>()
        val state by screenModel.state.collectAsState()
        val scope = rememberCoroutineScope()

        val wcsOffsets: List<WcsOffset> = state.wcsUiModel?.wcsOffsets ?: emptyList()
        val selected = state.wcsUiModel?.selected
        state.wcsUiModel?.let { wcs ->
            Column(Modifier.padding(top = 16.dp).wrapContentHeight()) {
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
                        screenModel.setActiveWcs(it)
                        scope.launch {
                            delay(500)
                            sheetState.hide()
                        }
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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

        Column {
            Row(modifier = Modifier.weight(1f)) {
                val axisCoordinatesWidth = 750.dp
                val spindleAndFeedWidth = 758.dp

                Column(
                    modifier = Modifier.width(axisCoordinatesWidth),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    AxisCoordinates(
                        state.xCoordinateUiModel,
                        state.zCoordinateUiModel,
                        xToolOffsetsClicked = {
                            screenModel.openNumPad(
                                inputType = InputType.TOOL_X_COORDINATE,
                                onSubmitAction = screenModel::setToolOffsetX
                            )
                        },
                        zToolOffsetsClicked = {
                            screenModel.openNumPad(
                                inputType = InputType.TOOL_Z_COORDINATE,
                                onSubmitAction = screenModel::setToolOffsetZ
                            )
                        },
                        onZeroPosX = screenModel::setZeroPosX,
                        onZeroPosZ = screenModel::setZeroPosZ,
                        onToggleAbsRelX = screenModel::toggleXAbsRel,
                        onToggleAbsRelZ = screenModel::toggleZAbsRel,
                    )

                    state.spindleUiModel?.let {
                        SpindleStatusView(
                            uiModel = it,
                            modifier =
                            Modifier.width(spindleAndFeedWidth)
                                .padding(horizontal = 8.dp)
                                .clickable(onClick = { navigator.push(TurningSettingsScreen()) })
                        )
                    }
                    state.feedUiModel?.let {
                        FeedStatusView(
                            uiModel = it,
                            modifier =
                            Modifier.width(spindleAndFeedWidth)
                                .padding(horizontal = 8.dp)
                                .clickable(onClick = { navigator.push(TurningSettingsScreen()) })
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    state.virtualLimitsUiModel?.let {
                        VirtualLimitsStatusView(
                            virtualLimits = it,
                            modifier =
                            Modifier.fillMaxWidth()
                                .padding(8.dp)
                                .clickable(onClick = { navigator.push(VirtualLimitsScreen()) })
                        )
                    }
                    state.simpleCycleUiModel?.let {
                        with(it.simpleCycleParameters) {
                            SimpleCycleStatusView(
                                simpleCycleParameters = this,
                                modifier =
                                Modifier.fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable(onClick = { navigator.push(SimpleCyclesScreen(simpleCycle)) })
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.width(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            screenModel.openNumPad(InputType.WORKPIECE_ZERO_COORDINATE) {
                                screenModel.setWorkpieceZ(it)
                            }
                        },
                    ) {
                        Text("Set Z Datum")
                    }
                }
                Column {
                    Surface(
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
                        shadowElevation = 8.dp
                    ) {
                        val height = 120.dp
                        Row(
                            modifier = Modifier.height(height).padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HandWheelStatus(uiModel = state.handWheelsUiModel, modifier = Modifier.size(height))
                            VerticalDivider()
                            JoystickStatus(modifier = Modifier.size(height), isTaper = state.taperTurningActive)
                            VerticalDivider()
                            TaperStatusView(
                                taperAngle = state.taperTurningAngle,
                                modifier =
                                Modifier.clickable(
                                    enabled = state.taperTurningActive,
                                    onClick = { navigator.push(TaperSettingsScreen()) }
                                ),
                                taperTurningActive = state.taperTurningActive,
                                onCheckedChange = screenModel::setTaperTurningActive
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChuckView(modifier: Modifier = Modifier, chuckClicked: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.height(250.dp).width(150.dp).clickable { chuckClicked.invoke() },
        border = BorderStroke(1.dp, SolidColor(Color.DarkGray)),
        shadowElevation = 16.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Set:", fontSize = 14.sp, textAlign = TextAlign.Left)
            Text(text = "Actual:", fontSize = 14.sp, textAlign = TextAlign.Left)
        }
    }
}

@Composable
private fun AxisCoordinates(
    xCoordinate: CoordinateUiModel,
    zCoordinate: CoordinateUiModel,
    xToolOffsetsClicked: () -> Unit,
    zToolOffsetsClicked: () -> Unit,
    onZeroPosX: () -> Unit,
    onZeroPosZ: () -> Unit,
    onToggleAbsRelX: () -> Unit,
    onToggleAbsRelZ: () -> Unit,
    axisCoordinateHeight: Dp = 60.dp,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(bottomEnd = 8.dp),
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
                modifier = axisItemModifier
            )
        }
    }
}
