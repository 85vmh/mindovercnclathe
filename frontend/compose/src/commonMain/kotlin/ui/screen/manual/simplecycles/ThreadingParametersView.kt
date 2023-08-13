package ui.screen.manual.simplecycles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.listitem.DropDownSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import ui.widget.CycleParameter
import usecase.model.SimpleCycleParameters

@Composable
fun ThreadingParametersView(
    viewModel: SimpleCyclesScreenModel,
    parametersState: SimpleCycleParameters.ThreadingParameters
) {
    val items = remember{
        SimpleCycleParameters.ThreadingParameters.ThreadType.entries.map { it.name }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ThreadLocation(
            isExternalThread = parametersState.isExternal,
            onTypeChanged = { viewModel.setThreadLocation(it) }
        )

        DropDownSetting(
            settingName = "Thread Type",
            items = items,
            dropDownWidth = 90.dp,
            selectedItem = parametersState.threadType.name,
            onValueChanged = { selectedValue ->
                SimpleCycleParameters.ThreadingParameters.ThreadType.entries
                    .find { it.name == selectedValue }
                    ?.let {
                        viewModel.setThreadType(it)
                    }
            }
        )

        CycleParameter(
            parameterLabel = when (parametersState.threadType) {
                SimpleCycleParameters.ThreadingParameters.ThreadType.Metric -> "Pitch"
                SimpleCycleParameters.ThreadingParameters.ThreadType.Imperial -> "TPI"
            },
            inputType = InputType.THREAD_PITCH,
            value = parametersState.pitch,
            onValueChange = viewModel::setThreadPitch,
        )
        CycleParameter(
            parameterLabel = "Z End",
            inputType = InputType.Z_END,
            value = parametersState.zEnd,
            teachInLabel = "TeachIn Z",
            onValueChange = viewModel::setZEnd,
            teachInClicked = viewModel::teachInZEnd
        )

        if (parametersState.isExternal) {
            CycleParameter(
                parameterLabel = "Major Diameter",
                inputType = InputType.THREAD_MAJOR_DIAMETER,
                value = parametersState.majorDiameter,
                onValueChange = viewModel::setMajorDiameter,
                teachInLabel = "TeachIn X",
                teachInClicked = viewModel::teachInMajorDiameter
            )
            CycleParameter(
                parameterLabel = "Initial Depth",
                inputType = InputType.DOC,
                value = parametersState.firstPassDepth,
                onValueChange = viewModel::setFirstPassDepth,
            )
            CycleParameter(
                parameterLabel = "Final Depth",
                inputType = InputType.THREAD_FINAL_DEPTH,
                value = parametersState.finalDepth,
                onValueChange = viewModel::setFinalPassDepth,
                teachInLabel = "Calculate",
                teachInClicked = viewModel::calculateFinalDepth
            )
            CycleParameter(
                parameterLabel = "Minor Diameter",
                inputType = InputType.THREAD_MINOR_DIAMETER,
                value = parametersState.minorDiameter,
                onValueChange = viewModel::setMinorDiameter,
            )
        } else {
            CycleParameter(
                parameterLabel = "Minor Diameter",
                inputType = InputType.THREAD_MINOR_DIAMETER,
                value = parametersState.minorDiameter,
                onValueChange = viewModel::setMinorDiameter,
                teachInLabel = "TeachIn X",
                teachInClicked = viewModel::teachInMinorDiameter
            )
            CycleParameter(
                parameterLabel = "Initial Depth",
                inputType = InputType.DOC,
                value = parametersState.firstPassDepth,
                onValueChange = viewModel::setFirstPassDepth,
            )
            CycleParameter(
                parameterLabel = "Final Depth",
                inputType = InputType.THREAD_FINAL_DEPTH,
                value = parametersState.finalDepth,
                onValueChange = viewModel::setFinalPassDepth,
                teachInLabel = "Calculate",
                teachInClicked = viewModel::calculateFinalDepth
            )
            CycleParameter(
                parameterLabel = "Major Diameter",
                inputType = InputType.THREAD_MAJOR_DIAMETER,
                value = parametersState.majorDiameter,
                onValueChange = viewModel::setMajorDiameter,
            )
        }

        CycleParameter(
            parameterLabel = "Spring Passes",
            inputType = InputType.THREAD_SPRING_PASSES,
            value = parametersState.springPasses.toDouble(),
            onValueChange = {
                viewModel.setThreadSpringPasses(it.toInt())
            },
        )
    }
}


@Composable
fun ThreadLocation(
    isExternalThread: Boolean,
    onTypeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Row(
            Modifier
                .width(200.dp)
                .selectable(
                    selected = isExternalThread,
                    onClick = { onTypeChanged(true) }
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isExternalThread,
                onClick = { onTypeChanged(true) }
            )
            Text(text = "External Thread")
        }
        Row(
            Modifier
                .width(200.dp)
                .selectable(
                    selected = isExternalThread.not(),
                    onClick = { onTypeChanged(false) }
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isExternalThread.not(),
                onClick = { onTypeChanged(false) }
            )
            Text(text = "Internal Thread")
        }

    }
}
