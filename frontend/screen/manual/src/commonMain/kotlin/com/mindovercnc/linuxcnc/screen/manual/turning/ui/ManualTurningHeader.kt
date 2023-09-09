package com.mindovercnc.linuxcnc.screen.manual.turning.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.root.ManualRootComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ManualTurningComponent
import com.mindovercnc.linuxcnc.screen.manual.turning.ui.axis.AxisCoordinates
import com.mindovercnc.linuxcnc.widgets.SimpleCycleStatusUi

@Composable
fun ManualTurningHeader(
    rootComponent: ManualRootComponent,
    component: ManualTurningComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    Row(modifier = modifier) {
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            AxisCoordinates(
                state.axisCoordinates,
                xToolOffsetsClicked = {
                    component.openNumPad(
                        inputType = InputType.TOOL_X_COORDINATE,
                        onSubmitAction = component::setToolOffsetX
                    )
                },
                zToolOffsetsClicked = {
                    component.openNumPad(
                        inputType = InputType.TOOL_Z_COORDINATE,
                        onSubmitAction = component::setToolOffsetZ
                    )
                },
                onZeroPosX = component::setZeroPosX,
                onZeroPosZ = component::setZeroPosZ,
                onToggleAbsRelX = component::toggleXAbsRel,
                onToggleAbsRelZ = component::toggleZAbsRel,
            )

            state.spindleUiModel?.let {
                SpindleStatusView(
                    uiModel = it,
                    onClick = { rootComponent.openTurningSettings() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
            state.feedUiModel?.let {
                FeedStatusCard(
                    uiModel = it,
                    onClick = { rootComponent.openTurningSettings() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            state.virtualLimitsUiModel?.let {
                VirtualLimitsStatusView(
                    virtualLimits = it,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    onClick = { rootComponent.openVirtualLimits() }
                )
            }
            state.simpleCycleUiModel?.let {
                with(it.simpleCycleParameters) {
                    SimpleCycleStatusUi(
                        simpleCycleParameters = this,
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        onClick = { rootComponent.openSimpleCycles(simpleCycle) }
                    )
                }
            }
        }
    }
}
