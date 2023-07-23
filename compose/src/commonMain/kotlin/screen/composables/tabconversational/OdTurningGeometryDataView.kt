//package screen.composables.tabconversational
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import screen.uimodel.InputType
//import screen.viewmodel.OdTurningViewModel
//import usecase.model.TeachInAxis
//
//@Composable
//fun OdTurningGeometryData() {
//    val odTurningViewModel: OdTurningViewModel = OdTurningViewModel()
//    val data = remember { odTurningViewModel.getOdTurningDataState() }
//
//    Column(
//        modifier = Modifier.padding(horizontal = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        InputSetting(
//            inputType = InputType.X_START,
//            value = data.xInitial.value.toString(),
//            teachInAxis = TeachInAxis.X,
//            onTeachInClicked = {}
//        ) {
//            data.xInitial.value = it.toDouble()
//        }
//        InputSetting(
//            inputType = InputType.X_END,
//            value = data.xFinal.value.toString(),
//            teachInAxis = TeachInAxis.X,
//            onTeachInClicked = {}
//        ) {
//            data.xFinal.value = it.toDouble()
//        }
//        InputSetting(
//            inputType = InputType.Z_START,
//            value = data.zStart.value.toString(),
//            teachInAxis = TeachInAxis.Z,
//            onTeachInClicked = {}
//        ) {
//            data.zStart.value = it.toDouble()
//        }
//        InputSetting(
//            inputType = InputType.Z_END,
//            value = data.zEnd.value.toString(),
//            teachInAxis = TeachInAxis.Z,
//            onTeachInClicked = {}
//        ) {
//            data.zEnd.value = it.toDouble()
//        }
//        InputSetting(
//            inputType = InputType.FILLET_RADIUS,
//            value = data.fillet.value.toString(),
//            onTeachInClicked = {}
//        ) {
//            data.fillet.value = it.toDouble()
//        }
//    }
//}