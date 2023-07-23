package ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.composables.NumericInputField
import screen.uimodel.InputType
import screen.uimodel.NumericInputs

@Composable
fun NumericInputWithUnit(
    value: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
) {
    val params = NumericInputs.entries[inputType]!!

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
        params.unit?.let {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = it
            )
        }
    }
}