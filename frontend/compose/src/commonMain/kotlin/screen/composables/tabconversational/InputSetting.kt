package screen.composables.tabconversational

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.widgets.NumericInputWithUnit
import usecase.model.TeachInAxis

@Composable
fun InputSetting(
    value: String,
    onValueChanged: (String) -> Unit,
    inputType: InputType,
    modifier: Modifier = Modifier,
    alternativeLabel: String? = null,
    teachInAxis: TeachInAxis? = null,
    onTeachInClicked: () -> Unit = {},
) {
    val alignment = Alignment.CenterVertically

    Row(
        verticalAlignment = alignment,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = alignment,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = alternativeLabel ?: inputType.description)
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