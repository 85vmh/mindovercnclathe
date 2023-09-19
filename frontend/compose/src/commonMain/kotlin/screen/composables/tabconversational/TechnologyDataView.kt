//package screen.composables.tabconversational
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.ArrowForward
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import screen.composables.CuttingParametersView
//import screen.composables.DropDownSetting
//import screen.composables.NumericInputWithUnit
//import screen.uimodel.*
//import screen.viewmodel.OdTurningViewModel
//import screen.viewmodel.createViewModel
//import usecase.model.TeachInAxis
//
//@Composable
//fun TurningTechnologyDataView() {
//        val odTurningViewModel: OdTurningViewModel = createViewModel()
//        val data = remember { odTurningViewModel.getOdTurningDataState() }
//
//    Box(
//        modifier = Modifier.padding(8.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Column(
//                modifier = Modifier.width(400.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//
//                DropDownSetting(
//                    modifier = Modifier.width(400.dp),
//                    settingName = "Work Offset",
//                    items = Wcs.values().map { it.text },
//                    dropDownWidth = 90.dp,
//                    selected = data.wcs.value.text,
//                    onValueChanged = {
//                        data.wcs.value = Wcs.fromString(it)!!
//                    }
//                )
//                DropDownSetting(
//                    modifier = Modifier.width(400.dp),
//                    settingName = "Material",
//                    items = WorkpieceMaterial.values().map { it.text },
//                    dropDownWidth = 180.dp,
//                    selected = data.material.value.name,
//                    onValueChanged = {
//                        data.material.value = WorkpieceMaterial.fromString(it)!!
//                    }
//                )
//                DropDownSetting(
//                    modifier = Modifier.width(400.dp),
//                    settingName = "Cut Direction",
//                    items = CutDirection.values().map { it.name },
//                    dropDownWidth = 150.dp,
//                    selected = data.cutDirection.value.name,
//                    onValueChanged = {
//                        data.cutDirection.value = CutDirection.fromString(it)!!
//                    }
//                )
//
//                DropDownSetting(
//                    modifier = Modifier.width(400.dp),
//                    settingName = "Strategy",
//                    items = CuttingStrategy.values().map { it.text },
//                    dropDownWidth = 220.dp,
//                    selected = data.cuttingStrategy.value.text, onValueChanged = {
//                        data.cuttingStrategy.value = CuttingStrategy.fromText(it)!!
//                    })
//            }
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                InputSetting(
//                    modifier = Modifier.width(400.dp),
//                    inputType = InputType.TOOL_CLEARANCE, value = data.toolClearance.value.toString()
//                ) {
//                    data.toolClearance.value = it.toDouble()
//                }
//                InputSetting(
//                    modifier = Modifier.width(400.dp),
//                    inputType = InputType.CSS_MAX_RPM, value = data.spindleMaxSpeed.value.toString()
//                ) {
//                    data.spindleMaxSpeed.value = it.toDouble().toInt()
//                }
//            }
//        }
//        Row(
//            modifier = Modifier.align(Alignment.BottomStart),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            val cuttingParamsModifier = Modifier.width(400.dp)
//
//            if (data.cuttingStrategy.value.isRoughing) {
//                CuttingParametersView(
//                    state = data.roughingParameters!!,
//                    cuttingStrategy = CuttingStrategy.Roughing,
//                    modifier = cuttingParamsModifier
//                )
//            }
//            if (data.cuttingStrategy.value == CuttingStrategy.RoughingAndFinishing) {
//                Column(
//                    verticalArrangement = Arrangement.SpaceAround,
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 8.dp)
//                ) {
//                    Button(
//                        modifier = Modifier.fillMaxWidth(),
//                        onClick = {})
//                    {
//                        Text("Fill same values")
//                        Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
//                    }
//                    Button(
//                        modifier = Modifier.fillMaxWidth(),
//                        onClick = {})
//                    {
//                        Text("Fill half values")
//                        Icon(imageVector = Icons.Outlined.ArrowForward, contentDescription = null)
//                    }
//                }
//            }
//            if (data.cuttingStrategy.value.isFinishing) {
//                CuttingParametersView(
//                    state = data.finishingParameters!!,
//                    cuttingStrategy = CuttingStrategy.Finishing,
//                    modifier = cuttingParamsModifier
//                )
//            }
//        }
//    }
//}
//
