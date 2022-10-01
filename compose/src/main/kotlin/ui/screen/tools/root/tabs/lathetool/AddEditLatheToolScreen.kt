package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mindovercnc.model.LatheTool
import com.mindovercnc.model.ToolType
import di.rememberScreenModel
import org.kodein.di.bindProvider
import screen.composables.CardWithTitle
import screen.composables.DropDownInserts
import screen.composables.DropDownSetting
import screen.composables.VerticalDivider
import screen.uimodel.InputType
import ui.screen.manual.Manual
import ui.widget.ValueSetting

class AddEditLatheToolScreen(
    private val latheTool: LatheTool? = null,
    private val onChanges: () -> Unit
) : Manual(
    when (latheTool) {
        null -> "Add Lathe Tool"
        else -> "Edit Lathe Tool #${latheTool.toolId}"
    }
) {

    @Composable
    override fun Actions() {
        val screenModel: AddEditLatheToolScreenModel = when (latheTool) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { latheTool } }
        }

        val navigator = LocalNavigator.currentOrThrow
        IconButton(
            modifier = iconButtonModifier,
            onClick = {
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

    @Composable
    override fun Content() {
        val screenModel: AddEditLatheToolScreenModel = when (latheTool) {
            null -> rememberScreenModel()
            else -> rememberScreenModel { bindProvider { latheTool } }
        }

        val state by screenModel.state.collectAsState()

        AddEditLatheToolContent(
            state,
            screenModel
        )
    }
}

@Composable
private fun AddEditLatheToolContent(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ValueSetting(
            settingName = "Lathe Tool ID",
            value = (state.latheToolId ?: 0).toString(),
            inputType = InputType.TOOL_ID,
            onValueChanged = {
                val doubleValue = it.toDouble()
                screenModel.setToolId(doubleValue.toInt())
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropDownSetting(
            modifier = Modifier.fillMaxWidth(),
            dropDownWidth = 150.dp,
            settingName = "Tool Type",
            items = state.toolTypes,
            selectedItem = state.toolType,
            onValueChanged = { screenModel.setToolType(it) }
        )

        when (state.toolType) {
            ToolType.Turning,
            ToolType.Boring -> TurningBoringToolProperties(state, screenModel, state.toolType == ToolType.Boring)
            ToolType.Drilling,
            ToolType.Reaming -> DrillingReamingToolProperties(state, screenModel)
            ToolType.Parting,
            ToolType.Grooving -> PartingGroovingToolProperties(state, screenModel)
            ToolType.OdThreading,
            ToolType.IdThreading -> ThreadingToolProperties(state, screenModel)
            ToolType.Slotting -> SlottingToolProperties(state, screenModel)
            else -> Unit
        }
    }
}

@Composable
private fun TurningBoringToolProperties(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel,
    isBoring: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DropDownInserts(
                dropDownWidth = 200.dp,
                settingName = "Insert Type",
                items = state.cuttingInserts,
                selected = state.cuttingInsert,
                onValueChanged = screenModel::setCuttingInsert
            )

            CardWithTitle(
                cardTitle = "Orientation & Angles",
                modifier = Modifier.width(400.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ToolOrientationPicker(
                        state.toolOrientation,
                        orientationSelected = screenModel::setToolOrientation
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ValueSetting(
                            settingName = "Front Angle",
                            value = state.frontAngle.toString(),
                            inputType = InputType.FRONT_ANGLE,
                            onValueChanged = {
                                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                                screenModel.setFrontAngle(doubleValue.toInt())
                            },
                            inputModifier = Modifier.width(50.dp)
                        )
                        ValueSetting(
                            settingName = "Back Angle",
                            value = state.backAngle.toString(),
                            inputType = InputType.BACK_ANGLE,
                            onValueChanged = {
                                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                                screenModel.setBackAngle(doubleValue.toInt())
                            },
                            inputModifier = Modifier.width(50.dp)
                        )
                    }
                }
            }
        }
        VerticalDivider()
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SpindleDirection(
                selectedDirection = state.spindleDirection,
                onDirectionSelected = screenModel::setSpindleDirection
            )
            if (isBoring) {
                ValueSetting(
                    settingName = "Min Bore Diameter",
                    value = state.minBoreDiameter.toString(),
                    inputType = InputType.MIN_BORE_DIAMETER,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        screenModel.setMinBoreDiameter(doubleValue)
                    },
                    inputModifier = Modifier.width(50.dp)
                )

                ValueSetting(
                    settingName = "Max Z Depth",
                    value = state.maxZDepth.toString(),
                    inputType = InputType.MAX_Z_DEPTH,
                    onValueChanged = {
                        val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                        screenModel.setMaxZDepth(doubleValue)
                    },
                    inputModifier = Modifier.width(50.dp)
                )
            }
        }
    }

}

@Composable
private fun DrillingReamingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel,
) {
    ValueSetting(
        settingName = "Tool Diameter",
        value = state.minBoreDiameter.toString(),
        inputType = InputType.TOOL_DIAMETER,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setToolDiameter(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max Z Depth",
        value = state.maxZDepth.toString(),
        inputType = InputType.MAX_Z_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setMaxZDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun PartingGroovingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel,
) {
    ValueSetting(
        settingName = "Blade width",
        value = state.bladeWidth.toString(),
        inputType = InputType.BLADE_WIDTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setBladeWidth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max X Depth",
        value = state.maxXDepth.toString(),
        inputType = InputType.MAX_X_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setMaxXDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun ThreadingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel,
) {
    ValueSetting(
        settingName = "Minimum Thread Pitch",
        value = state.minThreadPitch.toString(),
        inputType = InputType.THREADING_MIN_PITCH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setMinThreadPitch(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Maximum Thread Pitch",
        value = state.maxThreadPitch.toString(),
        inputType = InputType.THREADING_MAX_PITCH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setMaxThreadPitch(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun SlottingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    screenModel: AddEditLatheToolScreenModel,
) {
    ValueSetting(
        settingName = "Blade width",
        value = state.bladeWidth.toString(),
        inputType = InputType.BLADE_WIDTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setBladeWidth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max Z Depth",
        value = state.maxZDepth.toString(),
        inputType = InputType.MAX_Z_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            screenModel.setMaxZDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

//@Composable
//@Preview
//fun AddEditLatheToolContentPreview() {
//    AddEditLatheToolContent(
//        AddEditLatheToolScreenModel.State(),
//
//    )
//}