package ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import extensions.toFixedDigitsString
import screen.composables.NumericInputField
import screen.uimodel.InputType

@Composable
fun CycleParameter(
    parameterLabel: String,
    inputType: InputType,
    value: Double,
    teachInLabel: String? = null,
    onValueChange: (Double) -> Unit,
    teachInClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = parameterLabel
        )
        NumericInputField(
            numericValue = value.toFixedDigitsString(),
            inputType = inputType,
            modifier = Modifier.width(100.dp).padding(start = 16.dp)
        ) {
            onValueChange.invoke(it.toDouble())
        }

        teachInLabel?.let {
            Button(
                modifier = Modifier.padding(start = 16.dp),
                onClick = teachInClicked
            ) {
                Text(teachInLabel)
            }
        }
    }
}