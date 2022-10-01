package screen.composables.tabtools

import androidx.compose.runtime.Composable
import screen.composables.tabconversational.InputSetting
import screen.uimodel.InputType
import usecase.model.AddEditToolState

@Composable
fun KeySlottingView(
    toolState: AddEditToolState
) {

    InputSetting(inputType = InputType.BLADE_WIDTH, value = toolState.bladeWidth.value.toString()) {
        toolState.bladeWidth.value = it.toDouble()
    }
}