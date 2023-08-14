package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.MadeOf
import screen.composables.VerticalDivider
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.listitem.DropDownSetting
import com.mindovercnc.linuxcnc.listitem.ValueSetting

@Composable
fun AddEditCuttingInsertScreenContent(
    screenModel: AddEditCuttingInsertScreenModel,
    state: AddEditCuttingInsertState,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Properties(state, screenModel, modifier = Modifier.weight(3f).widthIn(min = 120.dp))

        VerticalDivider()

        Column(
            modifier = Modifier.weight(7f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = "Feeds & Speeds",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            FeedsAndSpeedsTable(
                feedsAndSpeedsList = state.feedsAndSpeedsList,
                editableIndex = 3,
                onDelete = {},
                onEdit = {}
            )
        }
    }
}

private val inputModifier = Modifier.width(90.dp)

@Composable
private fun Properties(
    state: AddEditCuttingInsertState,
    screenModel: AddEditCuttingInsertScreenModel,
    modifier: Modifier = Modifier
) {
    val madeOfList = remember { MadeOf.entries }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            text = "Properties",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        DropDownSetting(
            modifier = Modifier.fillMaxWidth(),
            settingName = "Made Of",
            items = madeOfList,
            dropDownWidth = 150.dp,
            selectedItem = state.madeOf,
            onValueChanged = screenModel::setMadeOf
        )
        state.madeOf?.let {
            if (state.isCustomGroundTool) {
                ValueSetting(
                    settingName = "Tip Angle",
                    value = state.tipAngle.toString(),
                    inputType = InputType.TIP_ANGLE,
                    onValueChanged = {
                        val intValue = it.toIntOrNull() ?: return@ValueSetting
                        screenModel.setTipAngle(intValue)
                    },
                    inputModifier = inputModifier
                )

                ValueSetting(
                    settingName = "Tip Radius",
                    value = state.tipRadius.toString(),
                    inputType = InputType.TIP_RADIUS,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        screenModel.setTipRadius(doubleValue)
                    },
                    inputModifier = inputModifier
                )
            } else {
                StandardInsert(
                    state,
                    insertShapeChange = screenModel::setInsertShape,
                    insertClearanceChange = screenModel::setInsertClearance,
                    toleranceClassChange = screenModel::setToleranceClass,
                    mountingChipBreakerChange = screenModel::setMountingAndChipBreaker
                )
                ValueSetting(
                    settingName = "Tip Radius",
                    value = state.tipRadius.toString(),
                    inputType = InputType.TIP_RADIUS,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        screenModel.setTipRadius(doubleValue)
                    },
                    inputModifier = inputModifier
                )
            }
            ValueSetting(
                settingName = "Size",
                value = state.size.toString(),
                inputType = InputType.INSERT_SIZE,
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                    screenModel.setSize(doubleValue)
                },
                inputModifier = inputModifier
            )
        }
    }
}
