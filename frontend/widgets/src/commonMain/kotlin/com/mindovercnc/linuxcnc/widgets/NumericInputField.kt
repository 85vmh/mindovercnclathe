package com.mindovercnc.linuxcnc.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindovercnc.linuxcnc.format.toFixedDigitsString
import com.mindovercnc.linuxcnc.numpad.InputDialogView
import com.mindovercnc.linuxcnc.numpad.NumPadState
import com.mindovercnc.linuxcnc.numpad.data.InputType

@Composable
fun NumericInputField(
    numericValue: String,
    inputType: InputType,
    modifier: Modifier = Modifier,
    valueChanged: (String) -> Unit
) {
    var numPadState by remember { mutableStateOf<NumPadState?>(null) }

    BasicTextField(
        textStyle = TextStyle(fontSize = 16.sp, color = LocalContentColor.current),
        readOnly = true,
        enabled = false,
        value = numericValue.toDouble().toFixedDigitsString(inputType.maxDecimalPlaces),
        singleLine = true,
        modifier =
            modifier.width(100.dp).clickable {
                numPadState = NumPadState(numericValue.toDouble(), inputType)
            },
        onValueChange = valueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Box(
                modifier =
                    Modifier.height(40.dp)
                        .fillMaxWidth(1f)
                        .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(4.dp))
                        .padding(8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                it()
            }
        },
    )

    numPadState?.let {
        InputDialogView(
            numPadState = it,
            onCancel = { numPadState = null },
            onSubmit = { value ->
                valueChanged(value.toString())
                numPadState = null
            }
        )
    }
}
