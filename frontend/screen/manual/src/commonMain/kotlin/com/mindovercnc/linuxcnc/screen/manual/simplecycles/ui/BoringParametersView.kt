package com.mindovercnc.linuxcnc.screen.manual.simplecycles.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.simplecycles.SimpleCyclesComponent
import com.mindovercnc.model.SimpleCycleParameters

@Composable
fun BoringParametersView(
    viewModel: SimpleCyclesComponent,
    parametersState: SimpleCycleParameters.BoringParameters
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CycleParameter(
            parameterLabel = "X End",
            inputType = InputType.X_END,
            value = parametersState.xEnd,
            teachInLabel = "TeachIn X",
            onValueChange = viewModel::setXEnd,
            teachInClicked = viewModel::teachInXEnd
        )
        CycleParameter(
            parameterLabel = "Z End",
            inputType = InputType.Z_END,
            value = parametersState.zEnd,
            teachInLabel = "TeachIn Z",
            onValueChange = viewModel::setZEnd,
            teachInClicked = viewModel::teachInZEnd
        )
        CycleParameter(
            parameterLabel = "Depth of cut",
            inputType = InputType.DOC,
            value = parametersState.doc,
            onValueChange = viewModel::setDoc,
        )
        CycleParameter(
            parameterLabel = "Taper Angle",
            inputType = InputType.TAPER_ANGLE,
            value = parametersState.taperAngle,
            onValueChange = viewModel::setTaperAngle,
        )
        CycleParameter(
            parameterLabel = "Fillet Radius",
            inputType = InputType.FILLET_RADIUS,
            value = parametersState.filletRadius,
            onValueChange = viewModel::setFilletRadius,
        )
    }
}
