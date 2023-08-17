package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mindovercnc.linuxcnc.numpad.data.InputType

@Composable
fun NumericInputWithUnit(
    value: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {
        NumericInputField(
            numericValue = value,
            inputType = inputType,
            modifier = Modifier.weight(1f),
            onValueChanged = onValueChanged,
            showUnit = true
        )
    }
}
