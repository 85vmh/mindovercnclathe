package com.mindovercnc.linuxcnc.numpad

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val numPadKeyModifier = Modifier
    .padding(16.dp)
    .size(60.dp)

@Composable
fun NumPadView(
    state: NumPadState,
    modifier: Modifier = Modifier,
) {
    var numPadValue by state.stringValueState
    val signKey = state.signKey
    val dotKey = state.dotKey

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumPadRow(
            "1", "2", "3",
            onClick = { numPadValue += it }
        )
        NumPadRow(
            "4", "5", "6",
            onClick = { numPadValue += it }
        )
        NumPadRow(
            "7", "8", "9",
            onClick = { numPadValue += it }
        )
        NumPadRow(
            signKey, "0", dotKey,
            onClick = {
                when (it) {
                    "+/-" -> state.toggleSign()
                    "." -> state.addDecimalPlace()
                    else -> numPadValue += it
                }
            }
        )
    }
}

@Composable
fun NumPadRow(
    vararg keys: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        keys.forEach {
            if (it.isNotEmpty()) {
                NumPadKey(
                    key = it,
                    onClick = onClick,
                    modifier = numPadKeyModifier
                )
            } else {
                Spacer(modifier = numPadKeyModifier)
            }
        }
    }
}
