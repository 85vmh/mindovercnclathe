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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.MadeOf
import di.rememberScreenModel
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import org.kodein.di.bindProvider
import screen.uimodel.InputType
import ui.screen.manual.Manual
import ui.widget.handle.defaultSplitter
import ui.widget.listitem.DropDownSetting
import ui.widget.listitem.ValueSetting

class AddEditCuttingInsertScreen(
    private val cuttingInsert: CuttingInsert? = null, private val onChanges: () -> Unit
) : Manual(
    when (cuttingInsert) {
        null -> "Add Cutting Insert"
        else -> "Edit Cutting Insert #${cuttingInsert.code}"
    }
) {

    @Composable
    override fun Actions() {
        val screenModel: AddEditCuttingInsertScreenModel = when (cuttingInsert) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { cuttingInsert } }
        }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(modifier = iconButtonModifier, onClick = {
            screenModel.applyChanges()
            onChanges.invoke()
            navigator.pop()
        }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
            )
        }
    }

    @OptIn(ExperimentalSplitPaneApi::class)
    @Composable
    override fun Content() {
        val screenModel: AddEditCuttingInsertScreenModel = when (cuttingInsert) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { cuttingInsert } }
        }

        val state by screenModel.state.collectAsState()
        val splitPaneState = rememberSplitPaneState(initialPositionPercentage = 0.3f)

        HorizontalSplitPane(splitPaneState = splitPaneState) {
            first(minSize = 120.dp) {
                Properties(state, screenModel)
            }

            second {
                Column {
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

            defaultSplitter()
        }
    }
}

private val inputModifier = Modifier.width(90.dp)

@Composable
private fun Properties(
    state: AddEditCuttingInsertState,
    screenModel: AddEditCuttingInsertScreenModel
) {
    val madeOfList = remember { MadeOf.values().toList() }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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