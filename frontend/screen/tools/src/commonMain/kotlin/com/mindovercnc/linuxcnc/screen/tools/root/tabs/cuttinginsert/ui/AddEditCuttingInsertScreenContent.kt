package com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.linuxcnc.listitem.DropDownSetting
import com.mindovercnc.linuxcnc.listitem.ValueSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.AddEditCuttingInsertComponent
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.AddEditCuttingInsertState
import com.mindovercnc.linuxcnc.screen.tools.root.tabs.cuttinginsert.add.feedsandspeeds.AddEditFeedsAndSpeedsScreen
import com.mindovercnc.linuxcnc.tools.model.MadeOf
import com.mindovercnc.linuxcnc.widgets.VerticalDivider

@Composable
fun AddEditCuttingInsertScreenContent(
    component: AddEditCuttingInsertComponent,
    state: AddEditCuttingInsertState,
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow
    Row(modifier = modifier) {
        Properties(state, component, modifier = Modifier.weight(3f).widthIn(min = 120.dp))

        VerticalDivider()

        Column(modifier = Modifier.weight(7f)) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                text = "Feeds & Speeds",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            FeedsAndSpeedsTable(
                feedsAndSpeedsList = state.feedsAndSpeedsList,
                onDelete = {},
                onEdit = {
                    navigator.push(AddEditFeedsAndSpeedsScreen(
                        feedsAndSpeeds = it
                    ) {
                        component.reloadFeedsAndSpeeds()
                    })
                },
            )
        }
    }
}

private val inputModifier = Modifier.width(90.dp)

@Composable
private fun Properties(
    state: AddEditCuttingInsertState,
    component: AddEditCuttingInsertComponent,
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
            onValueChanged = component::setMadeOf
        )
        state.madeOf?.let {
            if (state.isCustomGroundTool) {
                ValueSetting(
                    settingName = "Tip Angle",
                    value = state.tipAngle.toString(),
                    inputType = InputType.TIP_ANGLE,
                    onValueChanged = {
                        val intValue = it.toIntOrNull() ?: return@ValueSetting
                        component.setTipAngle(intValue)
                    },
                    inputModifier = inputModifier
                )

                ValueSetting(
                    settingName = "Tip Radius",
                    value = state.tipRadius.toString(),
                    inputType = InputType.TIP_RADIUS,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        component.setTipRadius(doubleValue)
                    },
                    inputModifier = inputModifier
                )
            } else {
                StandardInsert(
                    state,
                    insertShapeChange = component::setInsertShape,
                    insertClearanceChange = component::setInsertClearance,
                    toleranceClassChange = component::setToleranceClass,
                    mountingChipBreakerChange = component::setMountingAndChipBreaker
                )
                ValueSetting(
                    settingName = "Tip Radius",
                    value = state.tipRadius.toString(),
                    inputType = InputType.TIP_RADIUS,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        component.setTipRadius(doubleValue)
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
                    component.setSize(doubleValue)
                },
                inputModifier = inputModifier
            )
        }
    }
}
