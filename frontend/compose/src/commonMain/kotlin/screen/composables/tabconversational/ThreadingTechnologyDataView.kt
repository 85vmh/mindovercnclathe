package screen.composables.tabconversational

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.listitem.DropDownSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType

private class ThreadTechnologyDataState(
    offset: String,
    threadType: String,
    metricPitch: Double,
) {
    val workOffset = mutableStateOf(offset)
    val threadType = mutableStateOf(threadType)
    val metricPitch = mutableStateOf(metricPitch)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadingTechnologyData() {
    val workOffsets = remember {
        listOf("G54", "G55", "G56", "G57", "G58", "G59", "G59.1", "G59.2", "G59.3")
    }
    val threadType = remember { listOf("Metric", "Imperial") }
    val metricThreads = remember { listOf(0.5) }

    val state = remember {
        ThreadTechnologyDataState(workOffsets.first(), threadType.first(), metricThreads.first())
    }

    Column(verticalArrangement = Arrangement.Top) {
        DropDownSetting(
            modifier = Modifier.weight(1f),
            settingName = "Work Offset",
            items = workOffsets,
            dropDownWidth = 90.dp,
            selectedItem = state.workOffset.value,
            onValueChanged = { state.workOffset.value = it }
        )
        Row() {
            RadioButton(selected = false, onClick = {})

            Text(modifier = Modifier.padding(start = 16.dp), text = "External")
            RadioButton(selected = false, onClick = {})

            Text(modifier = Modifier.padding(start = 16.dp), text = "Internal")
        }
        DropDownSetting(
            modifier = Modifier.weight(1f),
            settingName = "Thread Type",
            items = threadType,
            dropDownWidth = 90.dp,
            selectedItem = state.threadType.value,
            onValueChanged = { state.threadType.value = it }
        )
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.TOOL_NUMBER,
            value = "1",
            onValueChanged = {}
        )
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.RPM,
            value = "200",
            onValueChanged = {}
        )
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.TOOL_CLEARANCE,
            value = "1",
            onValueChanged = {}
        )
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_STARTS,
            value = "1",
            onValueChanged = {}
        )
        InputSetting(
            modifier = Modifier.weight(1f),
            inputType = InputType.THREAD_PITCH,
            value = "1",
            onValueChanged = {}
        )
    }
}
