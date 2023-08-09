package screen.composables.tabconversational

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.uimodel.InputType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadingGeometryData() {
    Column {
        Row(

        ) {
            RadioButton(selected = false, onClick = {

            })
            Text(
                modifier = Modifier.padding(start = 16.dp), text = "Right Hand"
            )
            RadioButton(selected = false, onClick = {

            })
            Text(
                modifier = Modifier.padding(start = 16.dp), text = "Left Hand"
            )
        }

        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_X_START, value = "1"
        ) {}
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_MINOR_DIAMETER, value = "1"
        ) {}
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_Z_START, value = "1"
        ) {}
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_Z_END, value = "1"
        ) {}
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_LEAD_LENGTH, value = "1"
        ) {}
    }


}