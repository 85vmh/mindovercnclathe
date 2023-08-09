package ui.screen.manual.simplecycles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screen.uimodel.InputType
import ui.widget.CycleParameter
import usecase.model.SimpleCycleParameters

@Composable
fun KeySlotParametersView(
    viewModel: SimpleCyclesScreenModel,
    parameters: SimpleCycleParameters.KeySlotParameters,
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
        CycleParameter(
            parameterLabel = "Key Depth",
            inputType = InputType.KEY_SLOT_DEPTH,
            value = parameters.xEnd,
            teachInLabel = "TeachIn Depth",
            onValueChange = viewModel::setXEnd,
            teachInClicked = viewModel::teachInXEnd
        )
        CycleParameter(
            parameterLabel = "Doc Increment",
            inputType = InputType.DOC,
            value = parameters.doc,
            onValueChange = viewModel::setDoc,
        )
        CycleParameter(
            parameterLabel = "Feed Per Minute",
            inputType = InputType.FEED_PER_MIN,
            value = parameters.feedPerMinute,
            onValueChange = viewModel::setKeySlotCuttingFeed,
        )
    }
}