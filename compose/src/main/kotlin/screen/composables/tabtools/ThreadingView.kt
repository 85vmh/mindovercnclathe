package screen.composables.tabtools

import androidx.compose.runtime.Composable
import screen.composables.tabconversational.InputSetting
import screen.uimodel.InputType
import usecase.model.AddEditToolState

@Composable
fun ThreadingView(
    toolState: AddEditToolState
) {

    InputSetting(inputType = InputType.TIP_RADIUS, value = toolState.tipRadius.value.toString()) {
        toolState.tipRadius.value = it.toDouble()
    }
}