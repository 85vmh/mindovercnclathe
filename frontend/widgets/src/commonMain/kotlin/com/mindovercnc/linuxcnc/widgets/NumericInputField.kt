package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.numpad.NumPadState
import com.mindovercnc.linuxcnc.numpad.data.InputType
import com.mindovercnc.linuxcnc.numpad.data.UnitType

@Composable
fun NumericInputField(
    numericValue: String,
    inputType: InputType,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    showUnit: Boolean = false
) {
    var numPadState by remember { mutableStateOf<NumPadState?>(null) }

    val unit = inputType.unit
    OutlinedTextField(
        textStyle = TextStyle(fontSize = 16.sp, color = LocalContentColor.current),
        readOnly = true,
        enabled = false,
        value = numericValue.toDouble().toFixedDigitsString(inputType.maxDecimalPlaces),
        singleLine = true,
        modifier =
            modifier.width(100.dp).clickable {
                numPadState = NumPadState(numericValue.toDouble(), inputType)
            },
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        //        decorationBox = { innerTextField ->
        //            Row(
        //                modifier =
        //                    Modifier.height(40.dp)
        //                        .fillMaxWidth(1f)
        //                        .border(BorderStroke(1.dp, Color.LightGray),
        // RoundedCornerShape(4.dp))
        //                        .padding(8.dp),
        //                verticalAlignment = Alignment.CenterVertically,
        //                horizontalArrangement = Arrangement.Start
        //            ) {
        //                inputType.unit?.let { unit -> Text(text = unit.value) }
        //                innerTextField()
        //            }
        //        },
        trailingIcon =
            if (unit == null || !showUnit) null
            else {
                { UnitText(unit) }
            }
    )

    numPadState?.let {
        InputDialogView(
            numPadState = it,
            onCancel = { numPadState = null },
            onSubmit = { value ->
                onValueChanged(value.toString())
                numPadState = null
            }
        )
    }
}

@Composable
private fun UnitText(unit: UnitType) {
    Text(unit.value)
}
