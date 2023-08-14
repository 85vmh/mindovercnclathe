package ui.screen.manual.simplecycles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import ui.widget.CycleParameter
import usecase.model.SimpleCycleParameters

@Composable
fun DrillingParametersView(
    viewModel: SimpleCyclesScreenModel,
    parameters: SimpleCycleParameters.DrillingParameters,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        CycleParameter(
            parameterLabel = "Z End",
            inputType = InputType.Z_END,
            value = parameters.zEnd,
            teachInLabel = "TeachIn Z",
            onValueChange = viewModel::setZEnd,
            teachInClicked = viewModel::teachInZEnd
        )
    }
}