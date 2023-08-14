package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            modifier = Modifier.weight(1f)
        ) {
            onValueChanged(it)
        }
        inputType.unit?.let {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = it
            )
        }
    }
}