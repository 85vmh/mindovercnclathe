package ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.uimodel.InputType

@Composable
fun ValueSetting(
    settingName: String,
    value: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier.width(200.dp)
) {
    val alignment = Alignment.CenterVertically
    Row(
        verticalAlignment = alignment,
        modifier = modifier
    ) {
        Text(
            modifier = Modifier,
            text = settingName
        )
        Spacer(modifier = Modifier.weight(1f))
        NumericInputWithUnit(
            value = value,
            inputType = inputType,
            verticalAlignment = alignment,
            onValueChanged = onValueChanged,
            modifier = inputModifier
        )
    }
}