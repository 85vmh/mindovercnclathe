package com.mindovercnc.linuxcnc.screen.manual.tapersettings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.listitem.ValueSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.tapersettings.TaperSettingsComponent
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle

@Composable
fun TaperSettingsScreenUi(component: TaperSettingsComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CardWithTitle("Known Angle") {
            ValueSetting(
                settingName = "Taper Angle",
                value = state.angle.toFixedDigitsString(),
                inputType = InputType.TAPER_ANGLE,
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                    component.setAngle(doubleValue)
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        CardWithTitle("Unknown Angle") {
            //                Row(
            //                    verticalAlignment = Alignment.CenterVertically
            //                ) {
            //                    RadioButton(
            //                        enabled =
            // viewModel.finderState.procedureStarted.value.not(),
            //                        selected = viewModel.finderState.traverseOnZ.value.not(),
            //                        onClick = { viewModel.finderState.traverseOnZ.value =
            // false }
            //                    )
            //                    Text("Traverse on X")
            //                }
            //                Row(
            //                    verticalAlignment = Alignment.CenterVertically
            //                ) {
            //                    RadioButton(
            //                        enabled =
            // viewModel.finderState.procedureStarted.value.not(),
            //                        selected = viewModel.finderState.traverseOnZ.value,
            //                        onClick = { viewModel.finderState.traverseOnZ.value = true
            // }
            //                    )
            //                    Text("Traverse on Z")
            //                }
            //
            //                Button(onClick = {
            //                    viewModel.startMeasuringAngle()
            //                }) {
            //                    Text("Start Measuring")
            //                }
            //                Button(onClick = {
            //                    openKeyboardState = NumPadState(
            //                        numInputParameters =
            // NumericInputs.entries[InputType.DIAL_INDICATOR_DISTANCE]!!,
            //                        inputType = InputType.DIAL_INDICATOR_DISTANCE
            //                    )
            //                }) {
            //                    Text("End Measuring")
            //                }
        }
    }
}
