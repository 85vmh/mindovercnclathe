package ui.screen.tools.root.tabs.lathetool

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindovercnc.model.CuttingInsert
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import com.mindovercnc.model.ToolType
import extensions.draggableScroll
import screen.composables.DropDownInserts
import screen.composables.VerticalDivider
import screen.uimodel.InputType
import ui.widget.listitem.ValueSetting

@Composable
fun AddEditLatheToolContent(
    state: AddEditLatheToolScreenModel.State,
    onToolId: (Int) -> Unit,
    onToolType: (ToolType) -> Unit,
    onCuttingInsert: (CuttingInsert) -> Unit,
    onToolOrientation: (TipOrientation) -> Unit,
    onToolDiameter: (Double) -> Unit,
    onBackAngle: (Int) -> Unit,
    onFrontAngle: (Int) -> Unit,
    onSpindleDirection: (SpindleDirection) -> Unit,
    onMinBoreDiameter: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
    onMaxXDepth: (Double) -> Unit,
    onBladeWidth: (Double) -> Unit,
    onMinThreadPitch: (Double) -> Unit,
    onMaxThreadPitch: (Double) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val toolTypeScrollState = rememberLazyGridState()
    val toolPropertiesScrollState = rememberLazyListState()

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "Tool Type",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            LazyVerticalGrid(
                modifier = modifier.draggableScroll(toolTypeScrollState, scope),
                state = toolTypeScrollState,
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Adaptive(120.dp),
            ) {
                items(state.toolTypes.size) { index ->
                    ToolTypeView(
                        modifier = Modifier.padding(8.dp),
                        type = state.toolTypes[index],
                        onClick = onToolType,
                        isSelected = state.toolTypes[index] == state.toolType
                    )
                }
            }
        }
        VerticalDivider()
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.latheToolId == null) {
                ValueSetting(
                    settingName = "Lathe Tool ID",
                    value = "0",
                    inputType = InputType.TOOL_ID,
                    onValueChanged = {
                        val doubleValue = it.toDouble()
                        onToolId(doubleValue.toInt())
                    },
                    modifier = Modifier.fillMaxWidth(),
                    inputModifier = Modifier.width(100.dp)
                )
                Divider()
                Spacer(Modifier.height(24.dp))
            }
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Tool properties",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )

            LazyColumn(
                modifier = Modifier.draggableScroll(toolPropertiesScrollState, scope),
                state = toolPropertiesScrollState
            ) {
                item {
                    when (state.toolType) {
                        ToolType.Turning,
                        ToolType.Boring -> TurningBoringToolProperties(
                            state = state,
                            isBoring = state.toolType == ToolType.Boring,
                            onCuttingInsert,
                            onToolOrientation = onToolOrientation,
                            onBackAngle = onBackAngle,
                            onFrontAngle = onFrontAngle,
                            onSpindleDirection = onSpindleDirection,
                            onMinBoreDiameter = onMinBoreDiameter,
                            onMaxZDepth = onMaxZDepth
                        )

                        ToolType.Drilling,
                        ToolType.Reaming -> DrillingReamingToolProperties(
                            state = state,
                            onToolDiameter = onToolDiameter,
                            onMaxZDepth = onMaxZDepth
                        )

                        ToolType.Parting,
                        ToolType.Grooving -> PartingGroovingToolProperties(
                            state = state,
                            onBladeWidth = onBladeWidth,
                            onMaxXDepth = onMaxXDepth
                        )

                        ToolType.OdThreading,
                        ToolType.IdThreading -> ThreadingToolProperties(
                            state = state,
                            onMinThreadPitch = onMinThreadPitch,
                            onMaxThreadPitch = onMaxThreadPitch
                        )

                        ToolType.Slotting -> SlottingToolProperties(
                            state = state,
                            onBladeWidth = onBladeWidth,
                            onMaxZDepth = onMaxZDepth
                        )

                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun TurningBoringToolProperties(
    state: AddEditLatheToolScreenModel.State,
    isBoring: Boolean = false,
    onCuttingInsert: (CuttingInsert) -> Unit,
    onToolOrientation: (TipOrientation) -> Unit,
    onBackAngle: (Int) -> Unit,
    onFrontAngle: (Int) -> Unit,
    onSpindleDirection: (SpindleDirection) -> Unit,
    onMinBoreDiameter: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DropDownInserts(
            modifier = Modifier.width(400.dp),
            dropDownWidth = 200.dp,
            settingName = "Insert Type",
            items = state.cuttingInserts,
            selected = state.cuttingInsert,
            onValueChanged = onCuttingInsert
        )
        Row(
            modifier = Modifier.width(700.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrientationAnglesCard(
                state = state,
                orientationSelected = onToolOrientation,
                onBackAngleChanged = onBackAngle,
                onFrontAngleChanged = onFrontAngle,
                modifier = Modifier.width(400.dp)
            )
            SpindleDirection(
                selectedDirection = state.spindleDirection,
                onDirectionSelected = onSpindleDirection
            )
        }
        if (isBoring) {
            ValueSetting(
                settingName = "Min Bore Diameter",
                value = state.minBoreDiameter.toString(),
                inputType = InputType.MIN_BORE_DIAMETER,
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                    onMinBoreDiameter(doubleValue)
                },
                inputModifier = Modifier.width(50.dp)
            )

            ValueSetting(
                settingName = "Max Z Depth",
                value = state.maxZDepth.toString(),
                inputType = InputType.MAX_Z_DEPTH,
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                    onMaxZDepth(doubleValue)
                },
                inputModifier = Modifier.width(50.dp)
            )
        }
    }
}

@Composable
private fun DrillingReamingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    onToolDiameter: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
) {
    ValueSetting(
        settingName = "Tool Diameter",
        value = state.minBoreDiameter.toString(),
        inputType = InputType.TOOL_DIAMETER,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onToolDiameter(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max Z Depth",
        value = state.maxZDepth.toString(),
        inputType = InputType.MAX_Z_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onMaxZDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun PartingGroovingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    onBladeWidth: (Double) -> Unit,
    onMaxXDepth: (Double) -> Unit,
) {
    ValueSetting(
        settingName = "Blade width",
        value = state.bladeWidth.toString(),
        inputType = InputType.BLADE_WIDTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onBladeWidth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max X Depth",
        value = state.maxXDepth.toString(),
        inputType = InputType.MAX_X_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onMaxXDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun ThreadingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    onMinThreadPitch: (Double) -> Unit,
    onMaxThreadPitch: (Double) -> Unit,
) {
    ValueSetting(
        settingName = "Minimum Thread Pitch",
        value = state.minThreadPitch.toString(),
        inputType = InputType.THREADING_MIN_PITCH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onMinThreadPitch(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Maximum Thread Pitch",
        value = state.maxThreadPitch.toString(),
        inputType = InputType.THREADING_MAX_PITCH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onMaxThreadPitch(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}

@Composable
private fun SlottingToolProperties(
    state: AddEditLatheToolScreenModel.State,
    onBladeWidth: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
) {
    ValueSetting(
        settingName = "Blade width",
        value = state.bladeWidth.toString(),
        inputType = InputType.BLADE_WIDTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onBladeWidth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )

    ValueSetting(
        settingName = "Max Z Depth",
        value = state.maxZDepth.toString(),
        inputType = InputType.MAX_Z_DEPTH,
        onValueChanged = {
            val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
            onMaxZDepth(doubleValue)
        },
        inputModifier = Modifier.width(50.dp)
    )
}