package screen.composables.tabconversational

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.uimodel.InputType
import screen.uimodel.NumericInputs
import ui.widget.NumericInputWithUnit
import usecase.model.TeachInAxis

@Composable
fun InputSetting(
    value: String,
    inputType: InputType,
    alternativeLabel: String? = null,
    modifier: Modifier = Modifier,
    teachInAxis: TeachInAxis? = null,
    onTeachInClicked: () -> Unit = {},
    onValueChanged: (String) -> Unit
) {
    val alignment = Alignment.CenterVertically
    val params = NumericInputs.entries[inputType]!!

    Row(
        verticalAlignment = alignment,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = alignment,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = alternativeLabel ?: params.valueDescription
            )
        }
        NumericInputWithUnit(
            value = value,
            inputType = inputType,
            onValueChanged = onValueChanged,
            modifier = Modifier.width(170.dp),
            verticalAlignment = alignment
        )
        teachInAxis?.let {
            Button(onClick = onTeachInClicked) {
                Text("Teach ${it.name} Pos")
            }
        }
    }
}