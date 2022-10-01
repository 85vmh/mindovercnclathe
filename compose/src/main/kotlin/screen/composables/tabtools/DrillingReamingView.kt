package screen.composables.tabtools

import androidx.compose.runtime.Composable
import screen.composables.tabconversational.InputSetting
import screen.uimodel.InputType
import usecase.model.AddEditToolState

@Composable
fun DrillingReamingView(
    toolState: AddEditToolState
) {

    InputSetting(inputType = InputType.TOOL_DIAMETER, value = toolState.diameter.value.toString()) {
        toolState.diameter.value = it.toDouble()
    }
}