package com.mindovercnc.linuxcnc.numpad

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputDialogView(numPadState: NumPadState, onCancel: () -> Unit, onSubmit: (Double) -> Unit) {
    val valueAsString by numPadState.stringValueState
    val inputParams = numPadState.numInputParameters

    AlertDialog(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        onDismissRequest = {},
        text = {
            Column {
                Text(text = inputParams.description, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = valueAsString,
                    textStyle = TextStyle(fontSize = 24.sp, textAlign = TextAlign.End),
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        IconButton(modifier = Modifier.size(48.dp), onClick = { numPadState.clearAll() }) {
                            Icon(Icons.Default.Delete, contentDescription = "")
                        }
                    },
                    trailingIcon = {
                        IconButton(modifier = Modifier.size(48.dp), onClick = { numPadState.deleteChar() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "")
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                NumPadView(modifier = Modifier.fillMaxWidth(), state = numPadState)
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(valueAsString.toDouble()) },
                enabled = valueAsString.toDoubleOrNull() != null
            ) {
                Text("Submit")
            }
        },
        dismissButton = { Button(onClick = onCancel) { Text("Cancel") } },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )
}
