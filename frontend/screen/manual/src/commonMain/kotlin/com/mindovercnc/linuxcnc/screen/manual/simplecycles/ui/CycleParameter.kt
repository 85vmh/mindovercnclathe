package com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.widgets.NumericInputField

@Composable
fun CycleParameter(
    inputType: InputType,
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    teachInLabel: String? = null,
    teachInClicked: () -> Unit = {},
    parameterLabel: String = "",
    parameterAnnotatedLabel: AnnotatedString? = null
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        when {
            parameterAnnotatedLabel != null ->
                Text(modifier = Modifier.padding(start = 8.dp), text = parameterAnnotatedLabel)
            else -> Text(modifier = Modifier.padding(start = 8.dp), text = parameterLabel)
        }
        NumericInputField(
            numericValue = value.toFixedDigitsString(),
            inputType = inputType,
            modifier = Modifier.width(100.dp).padding(start = 16.dp),
            onValueChanged = { onValueChange.invoke(it.toDouble()) }
        )

        teachInLabel?.let {
            Button(modifier = Modifier.padding(start = 16.dp), onClick = teachInClicked) {
                Text(teachInLabel)
            }
        }
    }
}
