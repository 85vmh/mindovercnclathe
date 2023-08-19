package com.mindovercnc.linuxcnc.screen.tools.root.tabs.lathetool

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
import com.mindovercnc.linuxcnc.listitem.ValueSetting
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.widgets.VerticalDivider
import com.mindovercnc.linuxcnc.tools.model.CuttingInsert
import com.mindovercnc.linuxcnc.tools.model.ToolType
import com.mindovercnc.model.SpindleDirection
import com.mindovercnc.model.TipOrientation
import scroll.draggableScroll

private val inputModifier = Modifier.width(200.dp)

@Composable
fun AddEditLatheToolContent(
    state: AddEditLatheToolState,
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
    Row(modifier = modifier.fillMaxSize()) {
        StartContent(state, onToolType, modifier = Modifier.weight(2f).widthIn(min = 300.dp))

        VerticalDivider()

        EndContent(
            state,
            onToolId,
            onCuttingInsert,
            onToolOrientation,
            onToolDiameter,
            onBackAngle,
            onFrontAngle,
            onSpindleDirection,
            onMinBoreDiameter,
            onMaxZDepth,
            onMaxXDepth,
            onBladeWidth,
            onMinThreadPitch,
            onMaxThreadPitch,
            modifier = Modifier.weight(3f)
        )
    }
}

@Composable
private fun StartContent(
    state: AddEditLatheToolState,
    onToolType: (ToolType) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val toolTypeScrollState = rememberLazyGridState()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Tool Type",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        LazyVerticalGrid(
            modifier = Modifier.draggableScroll(toolTypeScrollState, scope),
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
}

@Composable
private fun EndContent(
    state: AddEditLatheToolState,
    onToolId: (Int) -> Unit,
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
    val toolPropertiesScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
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
                    ToolType.Boring -> {
                        TurningBoringToolProperties(
                            state = state,
                            isBoring = state.toolType == ToolType.Boring,
                            onCuttingInsert,
                            onToolOrientation = onToolOrientation,
                            onBackAngle = onBackAngle,
                            onFrontAngle = onFrontAngle,
                            onSpindleDirection = onSpindleDirection,
                            onMinBoreDiameter = onMinBoreDiameter,
                            onMaxZDepth = onMaxZDepth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    ToolType.Drilling,
                    ToolType.Reaming -> {
                        DrillingReamingToolProperties(
                            state = state,
                            onToolDiameter = onToolDiameter,
                            onMaxZDepth = onMaxZDepth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    ToolType.Parting,
                    ToolType.Grooving -> {
                        PartingGroovingToolProperties(
                            state = state,
                            onBladeWidth = onBladeWidth,
                            onMaxXDepth = onMaxXDepth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    ToolType.OdThreading,
                    ToolType.IdThreading -> {
                        ThreadingToolProperties(
                            state = state,
                            onMinThreadPitch = onMinThreadPitch,
                            onMaxThreadPitch = onMaxThreadPitch,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    ToolType.Slotting -> {
                        SlottingToolProperties(
                            state = state,
                            onBladeWidth = onBladeWidth,
                            onMaxZDepth = onMaxZDepth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun TurningBoringToolProperties(
    state: AddEditLatheToolState,
    isBoring: Boolean = false,
    onCuttingInsert: (CuttingInsert) -> Unit,
    onToolOrientation: (TipOrientation) -> Unit,
    onBackAngle: (Int) -> Unit,
    onFrontAngle: (Int) -> Unit,
    onSpindleDirection: (SpindleDirection) -> Unit,
    onMinBoreDiameter: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DropDownInserts(
            modifier = Modifier.fillMaxWidth(),
            dropDownWidth = 200.dp,
            settingName = "Insert Type",
            items = state.cuttingInserts,
            selected = state.cuttingInsert,
            onValueChanged = onCuttingInsert
        )
        Row(modifier = Modifier.width(700.dp), horizontalArrangement = Arrangement.SpaceBetween) {
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
                inputModifier = inputModifier
            )

            ValueSetting(
                settingName = "Max Z Depth",
                value = state.maxZDepth.toString(),
                inputType = InputType.MAX_Z_DEPTH,
                onValueChanged = {
                    val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                    onMaxZDepth(doubleValue)
                },
                inputModifier = inputModifier
            )
        }
    }
}

@Composable
private fun DrillingReamingToolProperties(
    state: AddEditLatheToolState,
    onToolDiameter: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ValueSetting(
            settingName = "Tool Diameter",
            value = state.minBoreDiameter.toString(),
            inputType = InputType.TOOL_DIAMETER,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onToolDiameter(doubleValue)
            },
            inputModifier = inputModifier
        )

        ValueSetting(
            settingName = "Max Z Depth",
            value = state.maxZDepth.toString(),
            inputType = InputType.MAX_Z_DEPTH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onMaxZDepth(doubleValue)
            },
            inputModifier = inputModifier
        )
    }
}

@Composable
private fun PartingGroovingToolProperties(
    state: AddEditLatheToolState,
    onBladeWidth: (Double) -> Unit,
    onMaxXDepth: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ValueSetting(
            settingName = "Blade width",
            value = state.bladeWidth.toString(),
            inputType = InputType.BLADE_WIDTH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onBladeWidth(doubleValue)
            },
            inputModifier = inputModifier
        )

        ValueSetting(
            settingName = "Max X Depth",
            value = state.maxXDepth.toString(),
            inputType = InputType.MAX_X_DEPTH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onMaxXDepth(doubleValue)
            },
            inputModifier = inputModifier
        )
    }
}

@Composable
private fun ThreadingToolProperties(
    state: AddEditLatheToolState,
    onMinThreadPitch: (Double) -> Unit,
    onMaxThreadPitch: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ValueSetting(
            settingName = "Minimum Thread Pitch",
            value = state.minThreadPitch.toString(),
            inputType = InputType.THREADING_MIN_PITCH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onMinThreadPitch(doubleValue)
            },
            inputModifier = inputModifier
        )

        ValueSetting(
            settingName = "Maximum Thread Pitch",
            value = state.maxThreadPitch.toString(),
            inputType = InputType.THREADING_MAX_PITCH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onMaxThreadPitch(doubleValue)
            },
            inputModifier = inputModifier
        )
    }
}

@Composable
private fun SlottingToolProperties(
    state: AddEditLatheToolState,
    onBladeWidth: (Double) -> Unit,
    onMaxZDepth: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ValueSetting(
            settingName = "Blade width",
            value = state.bladeWidth.toString(),
            inputType = InputType.BLADE_WIDTH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onBladeWidth(doubleValue)
            },
            inputModifier = inputModifier
        )

        ValueSetting(
            settingName = "Max Z Depth",
            value = state.maxZDepth.toString(),
            inputType = InputType.MAX_Z_DEPTH,
            onValueChanged = {
                val doubleValue = it.toDoubleOrNull() ?: return@ValueSetting
                onMaxZDepth(doubleValue)
            },
            inputModifier = inputModifier
        )
    }
}
