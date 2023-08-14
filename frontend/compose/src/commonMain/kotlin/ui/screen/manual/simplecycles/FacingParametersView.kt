package ui.screen.manual.simplecycles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import ui.widget.CycleParameter
import usecase.model.SimpleCycleParameters

@Composable
fun FacingParametersView(viewModel: SimpleCyclesScreenModel, parameters: SimpleCycleParameters.FacingParameters) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CycleParameter(
            parameterLabel = "X End",
            inputType = InputType.X_END,
            value = parameters.xEnd,
            teachInLabel = "TeachIn X",
            onValueChange = viewModel::setXEnd,
            teachInClicked = viewModel::teachInXEnd
        )
        CycleParameter(
            parameterLabel = "Z End",
            inputType = InputType.Z_END,
            value = parameters.zEnd,
            teachInLabel = "TeachIn Z",
            onValueChange = viewModel::setZEnd,
            teachInClicked = viewModel::teachInZEnd
        )
        CycleParameter(
            parameterLabel = "Depth of cut",
            inputType = InputType.DOC,
            value = parameters.doc,
            onValueChange = viewModel::setDoc,
        )
    }
}