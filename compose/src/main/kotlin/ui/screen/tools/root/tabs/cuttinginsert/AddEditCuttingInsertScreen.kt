package ui.screen.tools.root.tabs.cuttinginsert

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.FeedsAndSpeeds
import com.mindovercnc.model.MaterialCategory
import di.rememberScreenModel
import org.kodein.di.bindProvider
import screen.composables.DropDownSetting
import screen.composables.VerticalDivider
import screen.uimodel.InputType
import ui.screen.manual.Manual
import ui.widget.ValueSetting

class AddEditCuttingInsertScreen(
    private val cuttingInsert: CuttingInsert? = null,
    private val onChanges: () -> Unit
) :
    Manual(
        when (cuttingInsert) {
            null -> "Add Cutting Insert"
            else -> "Edit Cutting Insert #${cuttingInsert.code}"
        }
    ) {

    @Composable
    override fun Actions() {
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
                screenModel.applyChanges()
                onChanges.invoke()
                navigator.pop()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @Composable
    override fun Content() {
        val screenModel: AddEditCuttingInsertScreenModel =
            when (cuttingInsert) {
                null -> rememberScreenModel()
                else -> rememberScreenModel { bindProvider { cuttingInsert } }
            }

        val state by screenModel.state.collectAsState()
        val inputModifier = Modifier.width(90.dp)

        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // start
            Column(Modifier.weight(0.6f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Properties",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )

                DropDownSetting(
                    modifier = Modifier.fillMaxWidth(),
                    settingName = "Made Of",
                    items = state.madeOfList,
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

            VerticalDivider()

            // end
            Column(
                Modifier.weight(1.4f),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Feeds & Speeds",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(24.dp))

                FeedsAndSpeedsTable(
                    feedsAndSpeedsList = state.feedsAndSpeedsList,
                    editableIndex = 3,
                    onDelete = {},
                    onEdit = {}
                )
            }
        }
    }
}
