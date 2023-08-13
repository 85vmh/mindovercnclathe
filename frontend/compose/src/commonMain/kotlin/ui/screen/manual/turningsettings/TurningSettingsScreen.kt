package ui.screen.manual.turningsettings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.widgets.NumericInputField
import com.mindovercnc.linuxcnc.numpad.data.InputType
import di.rememberScreenModel
import screen.composables.cards.CardWithTitle
import ui.screen.manual.Manual

class TurningSettingsScreen : Manual("Turning Settings") {

    @Composable
    override fun RowScope.Actions() {
        val screenModel = rememberScreenModel<TurningSettingsScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<TurningSettingsScreenModel>()
        val modifier = Modifier

        Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CardWithTitle("Spindle") {
                SpindleDisplay(
                    screenModel = screenModel,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
            CardWithTitle("Feed") {
                FeedDisplay(
                    screenModel = screenModel,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }

    @Composable
    private fun SpindleDisplay(
        screenModel: TurningSettingsScreenModel,
        modifier: Modifier = Modifier
    ) {
        val state by screenModel.state.collectAsState()

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            RadioBoxSetting(
                settingName = "Constant Spindle Speed (RPM)",
                selected = state.isRpmActive,
                value = state.rpmValue.toString(),
                inputType = InputType.RPM,
                modifier =
                Modifier.fillMaxWidth()
                    .clickable(onClick = { screenModel.setRpmActive(true) })
                    .padding(start = 16.dp),
                onClicked = { screenModel.setRpmActive(true) },
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@RadioBoxSetting
                    screenModel.setRpmValue(doubleValue.toInt())
                }
            )
            RadioBoxSetting(
                settingName = "Constant Surface Speed (CSS)",
                selected = state.isRpmActive.not(),
                value = state.cssValue.toString(),
                inputType = InputType.CSS,
                modifier =
                Modifier.fillMaxWidth()
                    .clickable(onClick = { screenModel.setRpmActive(false) })
                    .padding(start = 16.dp),
                onClicked = { screenModel.setRpmActive(false) },
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@RadioBoxSetting
                    screenModel.setCssValue(doubleValue.toInt())
                }
            )
            ValueSetting(
                settingName = "Maximum spindle speed",
                value = state.maxSpeed.toString(),
                inputType = InputType.CSS_MAX_RPM
            ) {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                screenModel.setMaxSpeedValue(doubleValue.toInt())
            }

            CheckBoxSetting(
                settingName = "Oriented spindle stop",
                checked = state.isOrientActive,
                value = state.orientAngle.toString(),
                inputType = InputType.ORIENTED_STOP,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                onCheckedChange = { screenModel.setOrientActive(it) },
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@CheckBoxSetting
                    screenModel.setOrientAngle(doubleValue)
                }
            )
        }
    }

    @Composable
    private fun FeedDisplay(screenModel: TurningSettingsScreenModel, modifier: Modifier) {
        val state by screenModel.state.collectAsState()

        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            RadioBoxSetting(
                settingName = "Units per revolution",
                selected = state.isUnitPerRevActive,
                value = state.unitsPerRevValue.toString(),
                inputType = InputType.FEED_PER_REV,
                modifier =
                Modifier.fillMaxWidth()
                    .clickable(onClick = { screenModel.setUnitsPerRevActive(true) })
                    .padding(start = 16.dp),
                onClicked = { screenModel.setUnitsPerRevActive(true) },
                onValueChanged = {
                    val unitsPerRev = it.toDoubleOrNull() ?: return@RadioBoxSetting
                    screenModel.setUnitsPerRev(unitsPerRev)
                }
            )
            RadioBoxSetting(
                settingName = "Units per minute",
                selected = state.isUnitPerRevActive.not(),
                value = state.unitsPerMinValue.toString(),
                inputType = InputType.FEED_PER_MIN,
                modifier =
                Modifier.fillMaxWidth()
                    .clickable(onClick = { screenModel.setUnitsPerRevActive(false) })
                    .padding(start = 16.dp),
                onClicked = { screenModel.setUnitsPerRevActive(false) },
                onValueChanged = {
                    val unitsPerMin = it.toDoubleOrNull() ?: return@RadioBoxSetting
                    screenModel.setUnitsPerMin(unitsPerMin)
                }
            )
        }
    }

    @Composable
    private fun RadioBoxSetting(
        settingName: String,
        selected: Boolean,
        value: String,
        inputType: InputType,
        modifier: Modifier = Modifier,
        onClicked: () -> Unit,
        onValueChanged: (String) -> Unit
    ) {
        val alignment = Alignment.CenterVertically

        Row(verticalAlignment = alignment, modifier = modifier) {
            Row(verticalAlignment = alignment, modifier = Modifier.weight(1f)) {
                RadioButton(selected = selected, onClick = { onClicked() })
                Text(modifier = Modifier.padding(start = 16.dp), text = settingName)
            }
            NumericInputWithUnit(value, inputType, alignment, modifier = Modifier.width(200.dp)) {
                onValueChanged(it)
            }
        }
    }

    @Composable
    private fun CheckBoxSetting(
        inputType: InputType,
        settingName: String,
        checked: Boolean,
        value: String,
        modifier: Modifier = Modifier,
        onCheckedChange: (Boolean) -> Unit,
        onValueChanged: (String) -> Unit
    ) {
        val alignment = Alignment.CenterVertically
        ListItem(
            headlineContent = {
                Text(modifier = Modifier.padding(start = 16.dp), text = settingName)
            },
            leadingContent = {
                Checkbox(checked = checked, onCheckedChange)
            },
            trailingContent = {
                NumericInputWithUnit(value, inputType, alignment, modifier = Modifier.width(200.dp)) {
                    onValueChanged(it)
                }
            },
            modifier = modifier
        )
    }

    @Composable
    private fun ValueSetting(
        settingName: String,
        value: String,
        inputType: InputType,
        onValueChanged: (String) -> Unit
    ) {
        val alignment = Alignment.CenterVertically
        Row(verticalAlignment = alignment, modifier = Modifier.padding(start = 16.dp)) {
            Row(verticalAlignment = alignment, modifier = Modifier.weight(1f)) {
                Box(modifier = Modifier.size(48.dp))
                Text(modifier = Modifier.padding(start = 16.dp), text = settingName)
            }
            NumericInputWithUnit(value, inputType, alignment, modifier = Modifier.width(200.dp)) {
                onValueChanged(it)
            }
        }
    }

    @Composable
    private fun NumericInputWithUnit(
        value: String,
        inputType: InputType,
        alignment: Alignment.Vertical,
        modifier: Modifier = Modifier,
        onValueChanged: (String) -> Unit
    ) {

        Row(verticalAlignment = alignment, modifier = modifier) {
            NumericInputField(
                numericValue = value,
                inputType = inputType,
                modifier = Modifier.width(80.dp)
            ) {
                onValueChanged(it)
            }
            inputType.unit?.let { Text(modifier = Modifier.padding(start = 8.dp), text = it) }
        }
    }
}
