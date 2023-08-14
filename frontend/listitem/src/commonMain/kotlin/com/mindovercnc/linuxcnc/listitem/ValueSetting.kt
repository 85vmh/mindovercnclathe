package com.mindovercnc.linuxcnc.listitem

import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.widgets.NumericInputWithUnit

@Composable
fun ValueSetting(
    settingName: String,
    value: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier.width(200.dp)
) {
    ListItem(
        headlineContent = { Text(text = settingName) },
        trailingContent = {
            NumericInputWithUnit(
                value = value,
                inputType = inputType,
                verticalAlignment = Alignment.CenterVertically,
                onValueChanged = onValueChanged,
                modifier = inputModifier
            )
        },
        modifier = modifier
    )
}
