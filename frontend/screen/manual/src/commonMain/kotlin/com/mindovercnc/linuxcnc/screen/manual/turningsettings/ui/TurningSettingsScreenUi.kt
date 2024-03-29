package com.mindovercnc.linuxcnc.screen.manual.turningsettings.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.turningsettings.TurningSettingsComponent
import com.mindovercnc.linuxcnc.widgets.NumericInputWithUnit
import com.mindovercnc.linuxcnc.widgets.cards.CardWithTitle

@Composable
fun TurningSettingsScreenUi(screenModel: TurningSettingsComponent, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.padding(16.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
private fun SpindleDisplay(screenModel: TurningSettingsComponent, modifier: Modifier = Modifier) {
    val state by screenModel.state.collectAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RadioBoxSetting(
            settingName = "Constant Spindle Speed (RPM)",
            selected = state.isRpmActive,
            value = state.rpmValue.toString(),
            inputType = InputType.RPM,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            onClick = { screenModel.setRpmActive(true) },
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
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            onClick = { screenModel.setRpmActive(false) },
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@RadioBoxSetting
                screenModel.setCssValue(doubleValue.toInt())
            }
        )
        ValueSetting(
            settingName = "Maximum spindle speed",
            value = state.maxSpeed.toString(),
            inputType = InputType.CSS_MAX_RPM,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                screenModel.setMaxSpeedValue(doubleValue.toInt())
            }
        )

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
private fun RadioBoxSetting(
    settingName: String,
    selected: Boolean,
    value: String,
    inputType: InputType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    val alignment = Alignment.CenterVertically

    ListItem(
        leadingContent = { RadioButton(selected = selected, onClick = null) },
        headlineContent = { Text(modifier = Modifier.padding(start = 16.dp), text = settingName) },
        trailingContent = {
            NumericInputWithUnit(
                value = value,
                inputType = inputType,
                verticalAlignment = alignment,
                modifier = Modifier.width(200.dp),
                onValueChanged = onValueChanged
            )
        },
        modifier =
            modifier.selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
    )
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
        headlineContent = { Text(modifier = Modifier.padding(start = 16.dp), text = settingName) },
        leadingContent = { Checkbox(checked = checked, onCheckedChange) },
        trailingContent = {
            NumericInputWithUnit(
                value,
                inputType,
                verticalAlignment = alignment,
                modifier = Modifier.width(200.dp),
                onValueChanged = onValueChanged
            )
        },
        modifier =
            modifier.toggleable(checked, onValueChange = onCheckedChange, role = Role.Checkbox)
    )
}

@Composable
private fun ValueSetting(
    settingName: String,
    value: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val alignment = Alignment.CenterVertically
    ListItem(
        leadingContent = { Spacer(modifier = Modifier.size(48.dp)) },
        headlineContent = { Text(modifier = Modifier.padding(start = 16.dp), text = settingName) },
        trailingContent = {
            NumericInputWithUnit(
                value,
                inputType,
                verticalAlignment = alignment,
                modifier = Modifier.width(200.dp),
                onValueChanged = onValueChanged
            )
        },
        modifier = modifier
    )
}

@Composable
private fun FeedDisplay(screenModel: TurningSettingsComponent, modifier: Modifier) {
    val state by screenModel.state.collectAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        RadioBoxSetting(
            settingName = "Units per revolution",
            selected = state.isUnitPerRevActive,
            value = state.unitsPerRevValue.toString(),
            inputType = InputType.FEED_PER_REV,
            modifier = Modifier.fillMaxWidth(),
            onClick = { screenModel.setUnitsPerRevActive(true) },
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
            modifier = Modifier.fillMaxWidth(),
            onClick = { screenModel.setUnitsPerRevActive(false) },
            onValueChanged = {
                val unitsPerMin = it.toDoubleOrNull() ?: return@RadioBoxSetting
                screenModel.setUnitsPerMin(unitsPerMin)
            }
        )
    }
}
