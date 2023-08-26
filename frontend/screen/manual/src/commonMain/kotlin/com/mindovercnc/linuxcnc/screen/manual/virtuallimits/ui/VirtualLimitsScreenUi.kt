package com.mindovercnc.linuxcnc.screen.manual.virtuallimits.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.screen.manual.virtuallimits.VirtualLimitsComponent
import com.mindovercnc.linuxcnc.widgets.NumericInputField

@Composable
fun VirtualLimitsScreenUi(component: VirtualLimitsComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Limit(
            "X-",
            state.xMinusActive,
            state.xMinus,
            activeChange = { component.setXMinusActive(it) },
            onValueChanged = { component.setXMinus(it) }
        ) {
            component.teachInXMinus()
        }
        Limit(
            "X+",
            state.xPlusActive,
            state.xPlus,
            activeChange = { component.setXPlusActive(it) },
            onValueChanged = { component.setXPlus(it) }
        ) {
            component.teachInXPlus()
        }
        Limit(
            "Z-",
            state.zMinusActive,
            state.zMinus,
            activeChange = { component.setZMinusActive(it) },
            onValueChanged = { component.setZMinus(it) }
        ) {
            component.teachInZMinus()
        }
        Limit(
            "Z+",
            state.zPlusActive,
            state.zPlus,
            activeChange = { component.setZPlusActive(it) },
            onValueChanged = { component.setZPlus(it) }
        ) {
            component.teachInZPlus()
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.zPlusIsToolRelated,
                onClick = { component.setZPlusToolRelated(true) }
            )
            Text("Tool Tip Limit")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.zPlusIsToolRelated.not(),
                onClick = { component.setZPlusToolRelated(false) }
            )
            Text("Tailstock Limit")
        }
    }
}

@Composable
private fun Limit(
    axisDirection: String,
    active: Boolean,
    value: Double,
    activeChange: (Boolean) -> Unit,
    onValueChanged: (Double) -> Unit,
    teachIn: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = active, activeChange)
        Text(modifier = Modifier.padding(start = 8.dp), text = axisDirection)
        NumericInputField(
            numericValue = value.toFixedDigitsString(),
            inputType =
                when (axisDirection) {
                    "X-" -> InputType.VIRTUAL_LIMIT_X_MINUS
                    "X+" -> InputType.VIRTUAL_LIMIT_X_PLUS
                    "Z-" -> InputType.VIRTUAL_LIMIT_Z_MINUS
                    else -> InputType.VIRTUAL_LIMIT_Z_PLUS
                },
            modifier = Modifier.width(100.dp).padding(start = 16.dp),
            onValueChanged = { onValueChanged.invoke(it.toDouble()) }
        )

        Button(modifier = Modifier.padding(start = 16.dp), enabled = active, onClick = teachIn) {
            Text("Teach In $axisDirection")
        }
    }
}
