package screen.composables.tabtools

import androidx.compose.runtime.Composable
import screen.composables.tabconversational.InputSetting
import screen.uimodel.InputType
import usecase.model.AddEditToolState

@Composable
fun ProfilingView(
    toolState: AddEditToolState
) {

    InputSetting(inputType = InputType.TIP_RADIUS, value = toolState.tipRadius.value.toString()) {
        toolState.tipRadius.value = it.toDouble()
    }

//    InputSetting(inputType = InputType.TOOL_X_COORDINATE, value = toolState.xOffset.value.toString()) {
//        toolState.xOffset.value = it.toDouble()
//    }
//
//    InputSetting(inputType = InputType.TOOL_Z_COORDINATE, value = toolState.zOffset.value.toString()) {
//        toolState.zOffset.value = it.toDouble()
//    }
}

