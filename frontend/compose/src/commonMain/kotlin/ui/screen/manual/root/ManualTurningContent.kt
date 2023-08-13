package ui.screen.manual.root

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.mindovercnc.linuxcnc.numpad.data.InputType
import components.axis.AxisCoordinates
import ui.screen.manual.simplecycles.SimpleCyclesScreen
import ui.screen.manual.turningsettings.TurningSettingsScreen
import ui.screen.manual.virtuallimits.VirtualLimitsScreen
import ui.widget.simplecycle.SimpleCycleStatusUi

@Composable
fun ManualTurningContent(
    screenModel: ManualTurningScreenModel,
    state: ManualTurningState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ManualTurningHeader(
            screenModel,
            state,
            navigator,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        ManualTurningFooter(
            state,
            screenModel,
            navigator,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ManualTurningHeader(
    screenModel: ManualTurningScreenModel,
    state: ManualTurningState,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            AxisCoordinates(
                state.axisCoordinates,
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
                    onClick = { navigator.push(TurningSettingsScreen()) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
            state.feedUiModel?.let {
                FeedStatusView(
                    uiModel = it,
                    onClick = { navigator.push(TurningSettingsScreen()) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            state.virtualLimitsUiModel?.let {
                VirtualLimitsStatusView(
                    virtualLimits = it,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    onClick = { navigator.push(VirtualLimitsScreen()) }
                )
            }
            state.simpleCycleUiModel?.let {
                with(it.simpleCycleParameters) {
                    SimpleCycleStatusUi(
                        simpleCycleParameters = this,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        onClick = { navigator.push(SimpleCyclesScreen(simpleCycle)) }
                    )
                }
            }
        }
    }
}
