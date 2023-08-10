package ui.screen.manual.tapersettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import di.rememberScreenModel
import extensions.toFixedDigitsString
import screen.composables.cards.CardWithTitle
import screen.uimodel.InputType
import ui.screen.manual.Manual
import ui.widget.listitem.ValueSetting

class TaperSettingsScreen : Manual("Taper Settings") {

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<TaperSettingsScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<TaperSettingsScreenModel>()
        val state by screenModel.state.collectAsState()

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardWithTitle("Known Angle") {
                ValueSetting(
                    settingName = "Taper Angle",
                    value = state.angle.toFixedDigitsString(),
                    inputType = InputType.TAPER_ANGLE,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        screenModel.setAngle(doubleValue)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            CardWithTitle("Unknown Angle") {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    RadioButton(
//                        enabled = viewModel.finderState.procedureStarted.value.not(),
//                        selected = viewModel.finderState.traverseOnZ.value.not(),
//                        onClick = { viewModel.finderState.traverseOnZ.value = false }
//                    )
//                    Text("Traverse on X")
//                }
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    RadioButton(
//                        enabled = viewModel.finderState.procedureStarted.value.not(),
//                        selected = viewModel.finderState.traverseOnZ.value,
//                        onClick = { viewModel.finderState.traverseOnZ.value = true }
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
//                        numInputParameters = NumericInputs.entries[InputType.DIAL_INDICATOR_DISTANCE]!!,
//                        inputType = InputType.DIAL_INDICATOR_DISTANCE
//                    )
//                }) {
//                    Text("End Measuring")
//                }
            }
        }
    }
}